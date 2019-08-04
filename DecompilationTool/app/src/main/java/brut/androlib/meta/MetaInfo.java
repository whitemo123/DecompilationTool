/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.io.BufferedWriter
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.OutputStreamWriter
 *  java.io.Writer
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.nio.charset.Charset
 *  java.nio.charset.StandardCharsets
 *  java.util.Collection
 *  java.util.Map
 *  org.yaml.snakeyaml.DumperOptions
 *  org.yaml.snakeyaml.DumperOptions$FlowStyle
 *  org.yaml.snakeyaml.Yaml
 *  org.yaml.snakeyaml.constructor.BaseConstructor
 *  org.yaml.snakeyaml.introspector.PropertyUtils
 *  org.yaml.snakeyaml.representer.Representer
 */
package brut.androlib.meta;

import brut.androlib.meta.PackageInfo;
import brut.androlib.meta.StringExConstructor;
import brut.androlib.meta.StringExRepresent;
import brut.androlib.meta.UsesFramework;
import brut.androlib.meta.VersionInfo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.representer.Representer;

public class MetaInfo {
    public String apkFileName;
    public boolean compressionType;
    public Collection<String> doNotCompress;
    public boolean isFrameworkApk;
    public PackageInfo packageInfo;
    public Map<String, String> sdkInfo;
    public boolean sharedLibrary;
    public Map<String, String> unknownFiles;
    public UsesFramework usesFramework;
    public String version;
    public VersionInfo versionInfo;

    private static Yaml getYaml() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        StringExRepresent stringExRepresent = new StringExRepresent();
      //  stringExRepresent.getPropertyUtils().setSkipMissingProperties(true);
        StringExConstructor stringExConstructor = new StringExConstructor();
        Yaml yaml = new Yaml((BaseConstructor)stringExConstructor, (Representer)stringExRepresent, dumperOptions);
        return yaml;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static MetaInfo load(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        MetaInfo metaInfo = MetaInfo.load((InputStream)fileInputStream);
        if (fileInputStream == null) return metaInfo;
        if (false) {
            fileInputStream.close();
            return metaInfo;
        }
        fileInputStream.close();
        return metaInfo;
    }

    public static MetaInfo load(InputStream inputStream) {
        return (MetaInfo)MetaInfo.getYaml().load(inputStream);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void save(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
		Charset charset=Charset.forName("UTF-8");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)fileOutputStream, charset);
        BufferedWriter bufferedWriter = new BufferedWriter((Writer)outputStreamWriter);
        this.save((Writer)bufferedWriter);
        if (bufferedWriter != null) {
        
                bufferedWriter.close();
        
        }
        if (outputStreamWriter != null) {
         
           
                outputStreamWriter.close();
        
        }
        if (fileOutputStream != null) {
            if (!false) {
                fileOutputStream.close();
                return;
            }
            fileOutputStream.close();
        }
    }

    public void save(Writer writer) {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        MetaInfo.getYaml().dump((Object)this, writer);
    }
}


