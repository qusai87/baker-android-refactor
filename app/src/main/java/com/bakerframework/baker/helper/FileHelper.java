package com.bakerframework.baker.helper;

import android.util.Log;

import com.bakerframework.baker.BakerApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Copyright (c) 2013-2014. Francisco Contreras, Holland Salazar.
 * Copyright (c) 2015. Tobias Strebitzer, Francisco Contreras, Holland Salazar.
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * <p/>
 * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution.
 * Neither the name of the Baker Framework nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written
 * permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class FileHelper {

    public static String getContentsFromFile(File file) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            StringBuilder data = new StringBuilder("");
            while (in.read(buffer) != -1) {
                data.append(new String(buffer));
            }
            in.close();
            return data.toString();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private static void createNewFile(File targetFile) throws Exception {
        // Create directory structure
        if(!targetFile.getParentFile().exists() || !targetFile.getParentFile().isDirectory()) {
            targetFile.getParentFile().mkdirs();
        }
        if(!targetFile.exists()) {
            targetFile.createNewFile();
        }
        if(!targetFile.exists()) {
            throw new Exception("Unable to create target file");
        }
    }

    public static void createCacheFile(File cacheFile, String json) {
        try {
            Log.d("MLC-APP FileHelper", "Creating Cache File :" + cacheFile.getAbsolutePath());
            createNewFile(cacheFile);
            FileWriter fw = new FileWriter(cacheFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(json);
            bw.close();
            Log.d("MLC-APP FileHelper", "Create Cache File Success" + json);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MLC-APP FileHelper" , "Create Cache File failed " + e.getMessage());
            // on exception null will be returned
            cacheFile = null;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MLC-APP FileHelper", "Create Cache File failed " + e.getMessage());
        }
    }

    public static String getContentsFromInputStream(InputStream stream) {
        try {
            byte[] buffer = new byte[1024];
            StringBuilder data = new StringBuilder("");
            while (stream.read(buffer) != -1) {
                data.append(new String(buffer));
            }
            stream.close();
            return data.toString();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static JSONArray getJsonArrayFromFile(File file) {
        try {
            String content = getContentsFromFile(file);
            Log.d("MLC FileHelper", content);
            return new JSONArray(content);
        } catch (JSONException e) {
            return null;
        }
    }

    public static JSONObject getJsonObjectFromFile(File file) {
        try {
            return new JSONObject(getContentsFromFile(file));
        } catch (JSONException e) {
            return null;
        }
    }

    public static JSONObject getJsonObjectFromAsset(String assetPath) {
        try {
            InputStream inputStream = BakerApplication.getInstance().getAssets().open(assetPath);
            String fileContents = getContentsFromInputStream(inputStream);
            inputStream.close();
            return new JSONObject(fileContents);
        } catch (IOException e) {
            return null;
        } catch (JSONException e) {
            return null;
        }
    }


}
