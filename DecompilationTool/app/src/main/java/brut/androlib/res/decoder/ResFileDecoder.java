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

import brut.androlib.AndrolibException;
import brut.androlib.err.CantFind9PatchChunk;
import brut.androlib.res.data.ResResource;
import brut.androlib.res.data.value.ResBoolValue;
import brut.androlib.res.data.value.ResFileValue;
import brut.directory.DirUtil;
import brut.directory.Directory;
import brut.directory.DirectoryException;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ryszard Wiśniewski <brut.alll@gmail.com>
 */
public class ResFileDecoder {
    private final ResStreamDecoderContainer mDecoders;

    public ResFileDecoder(ResStreamDecoderContainer decoders) {
        this.mDecoders = decoders;
    }

    public void decode(ResResource res, Directory inDir, Directory outDir)
            throws AndrolibException {

        ResFileValue fileValue = (ResFileValue) res.getValue();
        String inFileName = fileValue.getStrippedPath();
        String outResName = res.getFilePath();
        String typeName = res.getResSpec().getType().getName();

	//	LOGGER.info("inName:"+inFileName+" out:"+outResName+" type:"+typeName);
        String ext = null;
        String outFileName;
        int extPos = inFileName.lastIndexOf(".");
        if (extPos == -1) {
            outFileName = outResName;
        } else {
            ext = inFileName.substring(extPos).toLowerCase();
            outFileName = outResName + ext;
        }

		
		if(ext==null)ext="";
		
	//	LOGGER.info("Decode :"+outFileName+" Type:"+typeName);
        try {
			
			
			
            if (typeName.equals("raw")) {
                decode(inDir, inFileName, outDir, outFileName, "raw");
                return;
            }
            if (typeName.equals("drawable") || typeName.equals("mipmap")) {
						if(!ext.equals(".png")&&!ext.equals(".xml"))
						{
							try
							{
								String TypeCheck=bytesToHexString(inDir.getFileInput(inFileName));
								if(TypeCheck!=null)
									if(TypeCheck.equals("8950")|| TypeCheck.equalsIgnoreCase("ffd8") )
										outFileName=outResName+".png";
								//LOGGER.info("发现drawable类型的.aac文件");
							}
							catch (DirectoryException e)
							{
								LOGGER.warning(e.toString());
							}



						}
				
                if (inFileName.toLowerCase().endsWith(".9" + ext)) {
                    outFileName = outResName + ".9" + ext;
                    // check for htc .r.9.png
                    if (inFileName.toLowerCase().endsWith(".r.9" + ext)) {
                        outFileName = outResName + ".r.9" + ext;
                    }

                    // check for samsung qmg & spi
                    if (inFileName.toLowerCase().endsWith(".qmg") || inFileName.toLowerCase().endsWith(".spi")) {
                        copyRaw(inDir, outDir, outFileName);
                        return;
                    }

                    // check for xml 9 patches which are just xml files
                    if (inFileName.toLowerCase().endsWith(".xml")) {
					
                        decode(inDir, inFileName, outDir, outFileName, "xml");
                        return;
                    }

                    try {
                        decode(inDir, inFileName, outDir, outFileName, "9patch");
                        return;
                    } catch (CantFind9PatchChunk ex) {
                        LOGGER.log(
                                Level.WARNING,
                                String.format(
                                        "Cant find 9patch chunk in file: \"%s\". Renaming it to *.png.",
                                        inFileName), ex);
                        outDir.removeFile(outFileName);
                        outFileName = outResName + ext;
                    }
                }
				
				
                if (!".xml".equals(ext)) {
                    decode(inDir, inFileName, outDir, outFileName, "raw");
                    return;
                }
            }

			if(!outFileName.endsWith(".xml"))
				outFileName+=".xml";
            decode(inDir, inFileName, outDir, outFileName, "xml");
        
			//try
		} 
		
		
		
		
		catch (AndrolibException ex) {
            LOGGER.log(Level.SEVERE, String.format(
                    "Could not decode file, replacing by FALSE value: %s",
                    inFileName), ex);
            res.replace(new ResBoolValue(false, 0, null));
        }
		
		
		
    }

    public void decode(Directory inDir, String inFileName, Directory outDir,
                       String outFileName, String decoder) throws AndrolibException {
      
	 //LOGGER.info(inDir.toString()+ "解压:"+inFileName);
		try
		{
			InputStream in = inDir.getFileInput(inFileName);
			OutputStream out = outDir.getFileOutput(outFileName);


            mDecoders.decode(in, out, decoder);
		}
		catch (DirectoryException e)
		{
			throw new AndrolibException(e);
		}
		
    }

    public void copyRaw(Directory inDir, Directory outDir, String filename) throws AndrolibException {
        try {
            DirUtil.copyToDir(inDir, outDir, filename);
        } catch (DirectoryException ex) {
            throw new AndrolibException(ex);
        }
    }

    public void decodeManifest(Directory inDir, String inFileName,
                               Directory outDir, String outFileName) throws AndrolibException {
      
		try
		{
			InputStream in = inDir.getFileInput(inFileName);
			OutputStream out = outDir.getFileOutput(outFileName);
			((XmlPullStreamDecoder) mDecoders.getDecoder("xml")).decodeManifest(in, out);
		}
		catch (DirectoryException e)
		{}
		
    }

    private final static Logger LOGGER = Logger.getLogger(ResFileDecoder.class.getName());
	
	public static String bytesToHexString(InputStream is)
	{
		
		byte[] src = new byte[2]; 
		try
		{
			is.read(src);
		}
		catch (IOException e)
		{
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) { return null; } 
		for (int i = 0; i < src.length; i++) 
		{ int v = src[i] & 0xFF; 
			String hv = Integer.toHexString(v); 
			if (hv.length() < 2) 
			{
				stringBuilder.append(0); 
			} stringBuilder.append(hv);
		}
		return stringBuilder.toString(); } 
	
}
