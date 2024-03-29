/*
 * This file is part of LauncherAPI (http://www.spout.org/).
 *
 * LauncherAPI is licensed under the SpoutDev License Version 1.
 *
 * LauncherAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * LauncherAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */

package org.spoutcraft.launcher.api;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import org.spoutcraft.launcher.api.security.CommonSecurityManager;
import org.spoutcraft.launcher.api.skin.CommonSkinManager;
import org.spoutcraft.launcher.api.skin.SkinManager;
import org.spoutcraft.launcher.api.util.FileUtils;

public class Launcher {
	private static Launcher instance;
	private final Logger logger = Logger.getLogger("org.spoutcraft.launcher.Main");
	private final SkinManager skinManager;
	private final double key;
	private final CommonSecurityManager security;
	private final DownloadManager downloads;
	private final GameUpdater updater;
	private final GameLauncher launcher;
	private OptionsFrame optionsFrame;

	public Launcher(final GameUpdater updater, final GameLauncher launcher, double key) {
		if (Launcher.instance != null)
			throw new IllegalArgumentException("You can have a duplicate Launcher");
		this.updater = updater;
		this.launcher = launcher;
		this.key = key;

		downloads = new DownloadManager(key);
		security = new CommonSecurityManager(key);

		logger.addHandler(new ConsoleHandler());

		System.setSecurityManager(security);

		skinManager = new CommonSkinManager(security, key);
		instance = this;
	}

	public static GameUpdater getGameUpdater() {
		if (instance == null) {
			System.out.println("instance is null");
		}
		if (instance.updater == null) {
			System.out.println("updater is null");
		}
		return instance.updater;
	}

	public static Logger getLogger() {
		return instance.logger;
	}

	public static SkinManager getSkinManager() {
		return instance.skinManager;
	}

	public static DownloadManager getDownloadManager() {
		return instance.downloads;
	}

	public static GameLauncher getGameLauncher() {
		return instance.launcher;
	}

	public static OptionsFrame getOptionsDialog() {
		return instance.optionsFrame;
	}

	public static void setOptionsFrame(OptionsFrame optionsFrame, double key) {
		if (key != instance.key)
			throw new IllegalArgumentException("Incorrect Key!");
		instance.optionsFrame = optionsFrame;
	}

	public static boolean clearCache() {
		try {
			FileUtils.deleteDirectory(instance.updater.getUpdateDir());
			FileUtils.deleteDirectory(instance.updater.getBinDir());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
