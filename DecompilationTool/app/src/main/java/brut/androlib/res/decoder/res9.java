
package brut.androlib.res.decoder;

import java.io.*;
import brut.androlib.AndrolibException;

/**
 * @author Ryszard Wiśniewski <brut.alll@gmail.com>
 */
public abstract class res9 implements ResStreamDecoder {
    @Override
    public abstract void decode(InputStream in, OutputStream out)
            throws AndrolibException; 
}
