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

package org.spoutcraft.launcher.api.util;

import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ImageUtils {

	public static void drawCharacter(JPanel contentPane, ActionListener listener, String url, int x, int y, List<JButton> buttons) {
		BufferedImage originalImage;
		try {
			try {
				originalImage = ImageIO.read(new URL(url));
			} catch (Exception e) {
				originalImage = ImageIO.read(new URL("http://s3.amazonaws.com/MinecraftSkins/char.png"));
			}
			int type = BufferedImage.TYPE_INT_ARGB;//originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

			buttons.add(drawCropped(contentPane, listener, originalImage, type, 40, 8, 48, 16, x - 4, y - 5, 8)); // HAT

			buttons.add(drawCropped(contentPane, listener, originalImage, type, 8, 8, 16, 16, x, y, 7)); // HEAD

			buttons.add(drawCropped(contentPane, listener, originalImage, type, 20, 20, 28, 32, x, y + 56, 7)); // BODY

			buttons.add(drawCropped(contentPane, listener, originalImage, type, 44, 20, 48, 32, x - 28, y + 56, 7)); // ARMS
			buttons.add(drawCropped(contentPane, listener, originalImage, type, 44, 20, 48, 32, x + 56, y + 56, 7, true));

			buttons.add(drawCropped(contentPane, listener, originalImage, type, 4, 20, 8, 32, x, y + 140, 7)); // LEGS
			buttons.add(drawCropped(contentPane, listener, originalImage, type, 4, 20, 8, 32, x + 28, y + 140, 7, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JButton drawCropped(JPanel contentPane, ActionListener listener, BufferedImage img, int type, int sx1, int sy1, int sx2, int sy2, int x, int y, int scale) {
		return drawCropped(contentPane, listener, img, type, sx1, sy1, sx2, sy2, x, y, scale, false);
	}

	public static JButton drawCropped(JPanel contentPane, ActionListener listener, BufferedImage img, int type, int sx1, int sy1, int sx2, int sy2, int x, int y, int scale, boolean reflect) {
		BufferedImage resizedImage = new BufferedImage((sx2 - sx1) * scale, (sy2 - sy1) * scale, type);
		Graphics2D g = resizedImage.createGraphics();
		int asx2 = sx2, asx1 = sx1;
		if (reflect) {
			asx2 = sx1;
			asx1 = sx2;
		}
		g.drawImage(img, 0, 0, (sx2 - sx1) * scale, (sy2 - sy1) * scale, asx1, sy1, asx2, sy2, null);
		g.dispose();

		JButton tmp = new JButton(new ImageIcon(resizedImage));
		tmp.setSelectedIcon(tmp.getIcon());
		tmp.setDisabledIcon(tmp.getPressedIcon());
		tmp.setPressedIcon(tmp.getIcon());

		tmp.setOpaque(false);
		tmp.setFocusable(false);

		tmp.setContentAreaFilled(false);
		tmp.setBorderPainted(false);
		tmp.setRolloverEnabled(false);

		tmp.setBounds(x, y, (sx2 - sx1) * scale, (sy2 - sy1) * scale);
		tmp.addActionListener(listener);
		contentPane.add(tmp);
		return tmp;
	}

}
