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

package brut.androlib;

import brut.androlib.src.*;
import brut.common.*;
import java.util.logging.*;

/**
 * @author Ryszard Wiśniewski <brut.alll@gmail.com>
 */
public class AndrolibException extends BrutException {
    public AndrolibException() {
    }

    public AndrolibException(String message) {
		
        super(message);
		
		
    }
	private final static Logger LOGGER = Logger.getLogger(SmaliBuilder.class.getName());
    public AndrolibException(String message, Throwable cause) {
       // super(message, cause);
		LOGGER.warning(message);
    }

    public AndrolibException(Throwable cause) {
        super(cause);
	
    }
}
