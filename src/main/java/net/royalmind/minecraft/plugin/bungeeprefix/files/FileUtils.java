package net.royalmind.minecraft.plugin.bungeeprefix.files;

import com.google.gson.Gson;
import lombok.experimental.UtilityClass;

import java.io.*;

@UtilityClass
public class FileUtils {

    public void saveFile(final String file, final Object object, final Gson gson) {
        try (final FileWriter fileWriter = new FileWriter(file)) {
            gson.toJson(object, fileWriter);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public Object loadFile(final String file, final Object object, final Gson gson) {
        Object obj = null;
        try {
            obj = gson.fromJson(new FileReader(file), object.getClass());
        } catch (final FileNotFoundException ignored) { }
        return obj;
    }

    public String toServerFile(final File dataFolder, final String fileName, final String extension) {
        return (dataFolder + File.separator + fileName + extension);
    }
}