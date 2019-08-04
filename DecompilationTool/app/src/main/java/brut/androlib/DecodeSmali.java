package brut.androlib;

import brut.androlib.src.SmaliDecoder;
import java.io.*;

public class DecodeSmali{
	public interface Dec{
		public void decode(File apkFile, File outDir, String filename,boolean bakdeb, int api) throws AndrolibException ;
	} 
	private static Dec dec = null;
	private static Dec dec1 = new Dec1();
	public static void setDec(Dec d){
		dec = d;
	}
	public static void decode(File apkFile, File outDir, String filename,boolean bakdeb, int api) throws AndrolibException {
		if(dec==null){
			dec1.decode(apkFile, outDir, filename, bakdeb, api);
		}else{
			dec.decode(apkFile, outDir, filename, bakdeb, api);
		}
	}
	private static class Dec1 implements Dec{
		public void decode(File apkFile, File outDir, String filename, boolean bakdeb, int api)  throws AndrolibException {
			SmaliDecoder.decode(apkFile, outDir, filename, bakdeb, api);
		}
	}
}
