package org.example.autoriaclone.dto.consts;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class ImageExtensionsConst {
    String[] extensions = new String[]{"jpg","jpeg","svg","webp","png"};
}
