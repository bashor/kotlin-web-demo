/*
 * Copyright 2000-2012 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.webdemo.translator;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.PsiFileFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jet.lang.psi.JetFile;
import org.jetbrains.jet.plugin.JetLanguage;
import org.jetbrains.k2js.config.Config;
import org.jetbrains.k2js.config.EcmaVersion;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class WebDemoConfigApplet extends Config {

    public WebDemoConfigApplet(@NotNull Project project) {
        super(project, REWRITABLE_MODULE_NAME, EcmaVersion.defaultVersion());
    }

    @NotNull
    protected List<JetFile> generateLibFiles() {
        List<JetFile> libFiles = new ArrayList<JetFile>();
        for (String libFileName : LIB_FILE_NAMES) {
            JetFile file = null;
            @SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
            InputStream stream = WebDemoConfigApplet.class.getResourceAsStream("/js" + libFileName);
            try {
                String text = FileUtil.loadTextAndClose(stream);
                file = (JetFile) PsiFileFactory.getInstance(getProject()).createFileFromText(libFileName, JetLanguage.INSTANCE, text, true, false);
                libFiles.add(file);
            } catch (IOException e) {
                System.err.println(libFileName);
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                System.err.println(libFileName);
                e.printStackTrace();
            }
        }
        return libFiles;
    }

}
