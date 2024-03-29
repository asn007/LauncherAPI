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

package org.spoutcraft.launcher.skin;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jdesktop.swingworker.SwingWorker;
import org.spoutcraft.launcher.api.util.Download;

public class BackgroundImageWorker extends SwingWorker<Object, Object> {
	private static final int IMAGE_CYCLE_TIME = 24 * 60 * 60 * 1000;
	private File backgroundImage;
	private JLabel background;

	public BackgroundImageWorker(File backgroundImage, JLabel background) {
		this.backgroundImage = backgroundImage;
		this.background = background;
	}

	@Override
	protected Object doInBackground() {
		try {
			if (!backgroundImage.exists() || backgroundImage.length() < 10 * 1024 || System.currentTimeMillis() - backgroundImage.lastModified() > IMAGE_CYCLE_TIME) {
				Download download = new Download("http://www.spoutcraft.org/splash/random.png", backgroundImage.getPath());
				download.run();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void done() {
		background.setIcon(new ImageIcon(backgroundImage.getPath()));
		background.setVerticalAlignment(SwingConstants.TOP);
		background.setHorizontalAlignment(SwingConstants.LEFT);
	}
}
