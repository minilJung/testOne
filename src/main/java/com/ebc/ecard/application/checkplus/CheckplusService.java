package com.ebc.ecard.application.checkplus;

import com.ebc.ecard.application.checkplus.dto.EcardUiCheckplusDto;

public interface CheckplusService {

    EcardUiCheckplusDto getToken() throws Exception;

}
