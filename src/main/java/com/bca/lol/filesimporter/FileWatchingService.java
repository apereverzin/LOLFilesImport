package com.bca.lol.filesimporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.bca.lol.filesimporter.directoryprocessor.FileImporter;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

/**
 * 
 * @author pereverzina
 */
public class FileWatchingService {

	private boolean keepWatching = true;
	FileImporter fileImporter = new FileImporter();

	public void watchDirectory(int languageId, String dirPath) throws IOException {
		System.out.println("Watching " + dirPath);
		
		WatchService watcher = FileSystems.getDefault().newWatchService();

		Path dir = new File(dirPath).toPath().toAbsolutePath();

		scanDir(languageId, dir);

		dir.register(watcher, ENTRY_CREATE);

		while (keepWatching) {
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException x) {
				return;
			}

			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();

				if (kind == OVERFLOW) {
					continue;
				}

				@SuppressWarnings("unchecked")
				WatchEvent<Path> ev = (WatchEvent<Path>) event;
				Path path = dir.resolve(ev.context());

				processPath(languageId, path);

				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			}
		}
	}

	public void stop() {
		keepWatching = false;
	}

	private void scanDir(int languageId, Path dir) {
		String[] children = dir.toFile().list();

		for (String child : children) {
			Path childDir = dir.resolve(child);
			processPath(languageId, childDir);
		}
	}

	private void processPath(int languageId, Path path) {
		if (Files.isDirectory(path)) {
			fileImporter.processDirectory(languageId, path);
		}
	}
}
