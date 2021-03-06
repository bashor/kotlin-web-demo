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

package org.jetbrains.webdemo.examplesLoader;

import org.json.JSONArray;

import java.util.List;
import java.util.Map;

public class ExamplesLoader {

    public ExamplesLoader() {
    }

    public String getExamplesList() {
        JSONArray response = new JSONArray();
        List<Map<String, String>> list = ExamplesList.getInstance().getList();
        for (Map<String, String> map : list) {
            ExampleObject example = ExamplesHolder.getExample(map.get("text"));
            if (example != null) {
                String dependencies = example.confType;
                if (dependencies != null) {
                    map.put("icon", dependencies);
                }
            }
            response.put(map);
        }
        return response.toString();
    }
}
