/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.movies.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

class Upload {
    static String saveTo(
        String folder,
        String name,
        InputStream content
    ) throws IOException {
        String filename = Paths.get(name).getFileName().toString();
        File file = new File(new File(folder), filename);
        Files.copy(content, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }
}
