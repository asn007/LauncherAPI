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

package org.spoutcraft.launcher.api.skin;

import java.io.File;

public abstract class JavaSkin implements Skin {
	private SkinDescriptionFile desc = null;
	private File file;
	private File dataFolder;
	private boolean enabled;
	private JavaSkinLoader loader;
	private CommonClassLoader classLoader;


	public final SkinDescriptionFile getDescription() {
		return desc;
	}

	public final File getDataFolder() {
		return dataFolder;
	}

	public final void initialize(JavaSkinLoader loader, SkinDescriptionFile desc, File dataFolder, File file, CommonClassLoader classLoader) {
		this.loader = loader;
		this.desc = desc;
		this.dataFolder = dataFolder;
		this.file = file;
		this.classLoader = classLoader;

	}

	protected final CommonClassLoader getClassLoader() {
		return classLoader;
	}

	public final void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public final boolean isEnabled() {
		return enabled;
	}

	public final SkinLoader getSkinLoader() {
		return loader;
	}

	public final File getFile() {
		return file;
	}
}
