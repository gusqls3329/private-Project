package com.example.beenproject.user;

import com.example.beenproject.common.Const;
import com.example.beenproject.common.exception.base.BadInformationException;
import com.example.beenproject.common.exception.checked.FileNotContainsDotException;
import com.example.beenproject.common.utils.MyFileUtils;
import com.example.beenproject.eneities.User;
import com.example.beenproject.eneities.enums.ProvideType;
import com.example.beenproject.eneities.enums.UserStatus;
import com.example.beenproject.user.model.SignUpDto;
import com.example.beenproject.user.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.beenproject.common.exception.ErrorMessage.BAD_PIC_EX_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final MyFileUtils myFileUtils;
    public int postSignup(SignUpDto dto){
        String password = passwordEncoder.encode(dto.getUpw());
        dto.setUpw(password);

        String path = "user" + "/" + dto.getIuser();
        myFileUtils.delFolderTrigger(path);
        if (dto.getPic() != null) {
            try {
                String savedPicFileNm = String.valueOf(
                        myFileUtils.savePic(dto.getPic(), "user",
                                String.valueOf(dto.getIuser())));
                dto.setChPic(savedPicFileNm);
            } catch (FileNotContainsDotException e) {
                throw new BadInformationException(BAD_PIC_EX_MESSAGE);
            }
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setNick(dto.getNick());
        user.setPic(dto.getChPic());
        user.setStatus(UserStatus.ACTIVE);
        user.setUid(dto.getUid());
        user.setUpw(dto.getUpw());
        user.setProvideType(ProvideType.LOCAL);
        repository.save(user);
        return (int) Const.SUCCESS;
    }
}
