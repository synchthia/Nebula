package net.synchthia.nebula.bukkit.sign;


import com.google.gson.Gson;
import lombok.NonNull;
import org.bukkit.block.Block;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author misterT2525
 */
public class SignManager {

    private static final Gson GSON = new Gson();

    private final Map<Block, SignData> signs = new HashMap<>();

    public void load(File file) throws IOException {
        SignData[] datas;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            datas = GSON.fromJson(br, SignData[].class);
        }

        signs.clear();

        for (SignData sign : datas) {
            Block block = sign.getBlock();
            String key = sign.getKey();
            if (block == null) {
                continue;
            }
            signs.put(block, sign);
        }
    }

    public void save(File file) throws IOException {
        String json = GSON.toJson(signs.values());

        try (FileWriter fw = new FileWriter(file)) {
            fw.write(json);
            fw.flush();
        }
    }

    public boolean add(@NonNull SignData sign) {
        if (sign.getBlock() == null) {
            return false;
        }
        signs.put(sign.getBlock(), sign);
        return true;
    }

    public void removeSign(@NonNull Block block) {
        signs.remove(block);
    }

    public Optional<SignData> findSign(@NonNull Block block) {
        SignData sign = signs.get(block);
        if (sign == null) {
            return Optional.empty();
        }
        Block signBlock = sign.getBlock();
        if (!block.equals(signBlock)) {
            signs.remove(block);
            return Optional.empty();
        }
        return Optional.of(sign);
    }

    public Collection<SignData> findAllSigns() {
        // Clean Data
        Iterator<Map.Entry<Block, SignData>> iterator = signs.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Block, SignData> entry = iterator.next();
            Block keyBlock = entry.getKey();
            Block signBlock = entry.getValue().getBlock();
            if (!keyBlock.equals(signBlock)) {
                System.out.println("null or not sign!");
                iterator.remove();
            }
        }

        return Collections.unmodifiableCollection(signs.values());
    }

    public Collection<SignData> findSigns(@NonNull String key) {
        return findAllSigns().stream()
                .filter(sign -> key.equalsIgnoreCase(sign.getKey()))
                .collect(Collectors.toList());
    }
}
