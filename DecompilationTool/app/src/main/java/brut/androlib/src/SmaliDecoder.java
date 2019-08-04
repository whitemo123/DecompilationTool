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

package brut.androlib.src;

import FormatFa.ApktoolHelper.*;
import brut.androlib.*;
import brut.common.*;
import java.io.*;
import java.util.logging.*;
import org.jf.baksmali.*;
import org.jf.dexlib2.*;
import org.jf.dexlib2.analysis.*;
import org.jf.dexlib2.dexbacked.*;

/**
 * @author Ryszard Wiśniewski <brut.alll@gmail.com>
 */
public class SmaliDecoder {

    public static void decode(File apkFile, File outDir, String dexName, boolean bakdeb, int api)
            throws AndrolibException {
        new SmaliDecoder(apkFile, outDir, dexName, bakdeb, api).decode();
    }

    private SmaliDecoder(File apkFile, File outDir, String dexName, boolean bakdeb, int api) {
        mApkFile = apkFile;
        mOutDir  = outDir;
        mDexFile = dexName;
        mBakDeb  = bakdeb;
        mApi     = api;
    }

    private void decode() throws AndrolibException {
        try {
            baksmaliOptions options = new baksmaliOptions();

            // options
            options.deodex = false;
            options.outputDirectory = mOutDir.toString();
            options.noParameterRegisters = false;
            options.useLocalsDirective = true;
            options.useSequentialLabels = true;
            options.outputDebugInfo = mBakDeb;
            options.addCodeOffsets =false;
            options.jobs = -1;
            options.noAccessorComments = true;
            options.registerInfo = 0;
            options.ignoreErrors = false;
            options.inlineResolver = null;
            options.checkPackagePrivateAccess = false;

            // set jobs automatically
            options.jobs = Runtime.getRuntime().availableProcessors();
			
            if (options.jobs > 6) {
                options.jobs = 6;
            }

            // create the dex
            DexBackedDexFile dexFile = DexFileFactory.loadDexFile(mApkFile, mDexFile, mApi, false);

            if (dexFile.isOdexFile()) {
                throw new AndrolibException("Warning: You are disassembling an odex file without deodexing it.");
            }

            if (dexFile instanceof DexBackedOdexFile) {
                options.inlineResolver =
                        InlineMethodResolver.createInlineMethodResolver(((DexBackedOdexFile)dexFile).getOdexVersion());
            }

            baksmali.disassembleDexFile(dexFile, options);
        } catch (IOException ex) {
			LOGGER.warning(ex.toString());
            throw new AndrolibException(ex);
        }
    }
	private final static Logger LOGGER = Logger.getLogger(BrutException.class.getName());
	

    private final File mApkFile;
    private final File mOutDir;
    private final String mDexFile;
    private final boolean mBakDeb;
    private final int mApi;
}
