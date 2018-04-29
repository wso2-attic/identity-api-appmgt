/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.integration.ballerina.rt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ballerinalang.BLangProgramLoader;
import org.ballerinalang.BLangProgramRunner;
import org.ballerinalang.connector.impl.ServerConnectorRegistry;
import org.ballerinalang.launcher.LauncherUtils;
import org.ballerinalang.util.BLangConstants;
import org.ballerinalang.util.codegen.ProgramFile;
import org.ballerinalang.util.exceptions.BallerinaException;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class BallerinaServiceHost {

    private static Log log = LogFactory.getLog(BallerinaServiceHost.class);

    private Path rootPath;
    private boolean isFileWatcherRunning;
    private Thread serviceWatcherThread;
    private WatchKey fileWatcherKey;
    private ServerConnectorRegistry serverConnectorRegistry;

    public BallerinaServiceHost(Path rootPath) {

        this.rootPath = rootPath;
    }

    public void start() {

        startServer();
        loadPrograms();
        signalDeploymentComplete();
        startWatching();
    }

    private void signalDeploymentComplete() {

        serverConnectorRegistry.deploymentComplete();
    }

    private void startServer() {

        serverConnectorRegistry = new ServerConnectorRegistry();
        serverConnectorRegistry.initServerConnectors();

    }

    public void stop() {

        stopWatching();
    }

    public void loadPrograms() {

        Set<Path> packages = new HashSet<>();
        try {
            loadPackages(rootPath, packages);

            for (Path pkg : packages) {
                addService(pkg);
            }
        } catch (IOException e) {
            log.error("Failed to load programs.", e);
        }

    }

    private void startWatching() {

        serviceWatcherThread = new Thread(() -> {

            try {
                WatchService watcher = FileSystems.getDefault().newWatchService();

                fileWatcherKey = rootPath.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                isFileWatcherRunning = true;
                while (isFileWatcherRunning) {
                    for (WatchEvent<?> event : fileWatcherKey.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();
                        if (kind == OVERFLOW) {
                            continue;
                        } else if (kind == ENTRY_CREATE) {
                            WatchEvent<Path> ev = (WatchEvent<Path>) event;
                            addService(ev.context());
                        } else if (kind == ENTRY_DELETE) {
                            WatchEvent<Path> ev = (WatchEvent<Path>) event;
                            removeService(ev.context());
                        } else if (kind == ENTRY_MODIFY) {
                            WatchEvent<Path> ev = (WatchEvent<Path>) event;
                            updateService(ev.context());
                        }
                    }
                }
            } catch (IOException e) {
                log.error("Failed to start watch.", e);
            }
        });

        serviceWatcherThread.start();
    }

    private void stopWatching() {

        isFileWatcherRunning = false;
        fileWatcherKey.cancel();

    }

    private void updateService(Path path) {

        if (log.isDebugEnabled()) {
            log.debug("Update service : " + path);
        }
    }

    private void removeService(Path path) {

        if (log.isDebugEnabled()) {
            log.debug("Remove service : " + path);
        }
    }

    private void addService(Path path) {

        log.info("Add service : " + path);
        ProgramFile programFile = loadProgram(path.toAbsolutePath(), false);
        programFile.setServerConnectorRegistry(serverConnectorRegistry);
        BLangProgramRunner.runService(programFile);
    }

    private ProgramFile loadProgram(Path fullPath, boolean offline) {

        ProgramFile programFile;
        String srcPathStr = fullPath.toString();
        if (srcPathStr.endsWith(BLangConstants.BLANG_EXEC_FILE_SUFFIX)) {
            programFile = BLangProgramLoader.read(fullPath);
        } else if (Files.isRegularFile(fullPath) && srcPathStr.endsWith(BLangConstants.BLANG_SRC_FILE_SUFFIX)) {
            programFile = LauncherUtils.compile(fullPath.getParent(), fullPath.getFileName(), offline);
        } else if (Files.isDirectory(fullPath)) {
            Path packagePath = rootPath.relativize(fullPath);
            programFile = LauncherUtils.compile(rootPath, packagePath, offline);
        } else {
            throw new BallerinaException(
                    "Invalid Ballerina source path, it should either be a directory or a file " + "with a \'"
                            + BLangConstants.BLANG_SRC_FILE_SUFFIX + "\' extension.");
        }

        return programFile;
    }

    void loadPackages(Path directory, final Collection<Path> collection) throws IOException {

        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {

            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {

                if (log.isDebugEnabled()) {
                    log.debug("Remove service : " + path);
                }
                if (path.getFileName().toString().endsWith(".bal")) {
                    collection.add(path.getParent());
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
