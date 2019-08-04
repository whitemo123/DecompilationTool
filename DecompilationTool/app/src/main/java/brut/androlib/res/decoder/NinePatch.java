package brut.androlib.res.decoder;

import java.io.*;
import brut.util.ExtDataInput;
import brut.androlib.AndrolibException;
import brut.androlib.err.CantFind9PatchChunk;

public class NinePatch {
        public final int padLeft, padRight, padTop, padBottom;
        public final int[] xDivs, yDivs;

        public NinePatch(int padLeft, int padRight, int padTop, int padBottom,
                         int[] xDivs, int[] yDivs) {
            this.padLeft = padLeft;
            this.padRight = padRight;
            this.padTop = padTop;
            this.padBottom = padBottom;
            this.xDivs = xDivs;
            this.yDivs = yDivs;
        }

        public static NinePatch decode(ExtDataInput di) throws IOException {
            di.skipBytes(1);
            byte numXDivs = di.readByte();
            byte numYDivs = di.readByte();
            di.skipBytes(1);
            di.skipBytes(8);
            int padLeft = di.readInt();
            int padRight = di.readInt();
            int padTop = di.readInt();
            int padBottom = di.readInt();
            di.skipBytes(4);
            int[] xDivs = di.readIntArray(numXDivs);
            int[] yDivs = di.readIntArray(numYDivs);

            return new NinePatch(padLeft, padRight, padTop, padBottom, xDivs,
                    yDivs);
        }
        

    public static NinePatch getNinePatch(byte[] data) throws AndrolibException,
            IOException {
        ExtDataInput di = new ExtDataInput(new ByteArrayInputStream(data));
        find9patchChunk(di);
        return NinePatch.decode(di);
    }

    private static void find9patchChunk(DataInput di) throws AndrolibException,
            IOException {
        di.skipBytes(8);
        while (true) {
            int size;
            try {
                size = di.readInt();
            } catch (IOException ex) {
                throw new CantFind9PatchChunk("Cant find nine patch chunk", ex);
            }
            if (di.readInt() == NP_CHUNK_TYPE) {
                return;
            }
            di.skipBytes(size + 4);
        }
    }
    private static final int NP_CHUNK_TYPE = 0x6e705463; // npTc
}
