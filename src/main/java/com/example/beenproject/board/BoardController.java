package com.example.beenproject.board;

import com.example.beenproject.board.model.BoardListDto;
import com.example.beenproject.board.model.BoardListVo;
import com.example.beenproject.board.model.BoradVo;
import com.example.beenproject.board.model.InsBoardDto;
import com.example.beenproject.common.ErrorMessage;
import com.example.beenproject.common.ResVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequestMapping("/api/board")
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService service;

    @PostMapping
    public ResVo postBoard(@RequestPart @NotNull(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
                               MultipartFile mainPic,
                           @RequestPart (required = false)
                           @Size(max = 4)
                           List<MultipartFile> pics,
                           InsBoardDto dto
                            ){
        dto.setMainPic(mainPic);
        dto.setPics(pics);
        return new ResVo(service.postBoard(dto));
    }

    @Operation(summary = "전체 게시글 목록", description = "결과값 :" +
            "[{" +
            "게시판 목록<br>nick:닉네임" +
            "<br>isLikes: [0]좋아요 안함, [1]좋아요 누름" +
            "<br>iboard: 게시글 pk" +
            "<br>title: 게시글 제목" +
            "<br>view: 게시글 조회수" +
            "<br>createdAt: 등록 날짜" +
            "<br>boardLikeCnt: 좋아요 받은 갯수}]")
    @Parameters(value = {
            @Parameter(name = "page", description = "페이지, min:1 / 게시글 12개씩 나옴"),
            @Parameter(name = "search", description = "search(검색어)가 제공될 경우 해당 키워드가 포함된 게시글만 조회<br>" +
                    "search(검색어) 와 type(제목, 제목+내용, 닉네임) 은 항상 동시에 값이 있거나 동시에 값이 없어야함."),
            @Parameter(name = "type", description = "type 은 search 하는 조건임<br>" +
                    "type 에는 title:1, title+contents:2, nick:3 (제목, 제목+내용, 닉네임)의 3종류가 있음 (숫자로 받고, 백엔드에서 해석)<br>" +
                    "type=1 이면 1에 해당하는 종류중 search 에 해당하는 키워드가 포함되어 있는 게시물을 페이징해서 넘겨주는 개념임."),
            @Parameter(name = "sort", description = "sort:0 (default) - 최신순<br>sort:1 - 좋아요순<br>sort:2 - 조회수 많은순")})
    @Validated
    @GetMapping
    public BoardListVo getBoardList(@RequestParam(defaultValue = "1") @Min(1)
                                    int page,
                                    @RequestParam(defaultValue = "0")
                                    int sort,
                                    @RequestParam(name = "search", required = false)
                                    String search,
                                    @RequestParam(name = "type", required = false)
                                    Integer type)
    {
        BoardListDto dto = BoardListDto.builder()
                .page(page)
                .search(search)
                .type(type)
                .sort(sort)
                .build();
        return service.getBoardList(dto);
    }
    @Operation(summary = "게시글 상세", description = "특정 게시글 입장")
    @Parameters(value = {
            @Parameter(name = "iboard", description = "게시글pk")})
    @GetMapping("/{iboard}")
    public BoradVo getBoard(@PathVariable int iboard) {
        return service.getBoard(iboard);
    }






}
