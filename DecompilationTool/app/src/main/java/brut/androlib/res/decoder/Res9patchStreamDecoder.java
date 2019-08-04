/**
 *  Copyright 2014 Ryszard Wiśniewski <brut.alll@gmail.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package brut.androlib.res.decoder;

//import FormatFa.ApktoolHelper.apktool.*;
import android.graphics.*;
import brut.androlib.*;
import java.io.*;
import java.util.logging.*;
import org.apache.commons.io.*;



//FormatFa  修改for Android
/**
 * @author Ryszard Wiśniewski <brut.alll@gmail.com>
 */
public class Res9patchStreamDecoder extends res9 {
	//private static final Logger LOGGER = Logger.getLogger(Res9.class.getName());

	@Override
	public void decode(InputStream in, OutputStream out)
	throws AndrolibException
	{
        try
		{
			byte[] data = IOUtils.toByteArray(in);

			Bitmap im=BitmapFactory.decodeByteArray(data,0,data.length);
            int w = im.getWidth(), h = im.getHeight();

			Bitmap im2 = Bitmap.createBitmap(w + 2, h + 2, im.getConfig());
			for (int x=0;x < w;x++)
			{
				for (int y=0;y < h;y++)
				{
					int color=im.getPixel(x, y);
					im2.setPixel(x + 1, y + 1, color);
				}
			}

            NinePatch np = NinePatch.getNinePatch(data);
            drawHLine(im2, h + 1, np.padLeft + 1, w - np.padRight);
            drawVLine(im2, w + 1, np.padTop + 1, h - np.padBottom);

            int[] xDivs = np.xDivs;
            for (int i = 0; i < xDivs.length; i += 2)
			{
                drawHLine(im2, 0, xDivs[i] + 1, xDivs[i + 1]);
            }

            int[] yDivs = np.yDivs;
            for (int i = 0; i < yDivs.length; i += 2)
			{
                drawVLine(im2, 0, yDivs[i] + 1, yDivs[i + 1]);
            }

			im2.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
		catch (IOException ex)
		{
            throw new AndrolibException(ex);
        }
		catch (NullPointerException ex)
		{
            // In my case this was triggered because a .png file was
            // containing a html document instead of an image.
            // This could be more verbose and try to MIME ?
            throw new AndrolibException(ex);
        }
    }

    private void drawHLine(Bitmap im, int y, int x1, int x2)
	{
        for (int x = x1; x <= x2; x++)
		{
            im.setPixel(x, y, NP_COLOR);
        }
    }

    private void drawVLine(Bitmap im, int x, int y1, int y2)
	{
        for (int y = y1; y <= y2; y++)
		{
            im.setPixel(x, y, NP_COLOR);
        }
    }

    private static final int NP_COLOR = 0xff000000;
    
}
