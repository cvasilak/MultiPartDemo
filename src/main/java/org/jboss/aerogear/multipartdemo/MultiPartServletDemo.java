/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.multipartdemo;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@WebServlet(name = "MultiPartServletDemo", urlPatterns = {"/upload"})
@MultipartConfig()
public class MultiPartServletDemo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for (Part p : req.getParts()) {
            System.out.println("Part name: " + p.getName());
            System.out.println("Size: " + p.getSize());
            System.out.println("Content Type: " + p.getContentType());
            System.out.print("Header Names:");
            for (String name : p.getHeaderNames()) {
                System.out.print(" " + name);
            }

            OutputStream out = null;
            InputStream filecontent = null;

            try {
                out = new FileOutputStream(new File("/Users/cvasilak/Desktop" + File.separator
                        +  p.getName()));
                filecontent = p.getInputStream();

                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println();
        }
    }
}
