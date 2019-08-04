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

package brut.common;

/**
 * @author Ryszard Wiśniewski <brut.alll@gmail.com>
 */
 
 
 
import brut.androlib.*;
import java.util.logging.*;
import android.media.*;

public class BrutException extends Exception
 {
    public BrutException(Throwable cause) {
        super(cause);
		StringBuilder sb=new StringBuilder();
StackTraceElement[] eles=cause.getStackTrace();
for(StackTraceElement item:eles)
{
sb.append(	"At "+item.getFileName()+" in "+item.getLineNumber()+" error:"+item.getMethodName());
	sb.append(item.toString());
	sb.append("\n");
	
}
		LOGGER.warning(sb.toString());
    }
	private final static Logger LOGGER = Logger.getLogger(BrutException.class.getName());
	
    public BrutException(String message, Throwable cause) {
        super(message, cause);
	
		LOGGER.warning(message);

    }

    public BrutException(String message) {
        super(message);
		LOGGER.warning(message);
    }

    public BrutException() {
    }
}
