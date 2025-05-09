package org.example.autoriaclone.dto.consts;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class BadWordsConst {
    String[] badWords = new String[]{"сук","нах","піда","долбо","єб","пізд","гандо","хуй","бля"};
}
