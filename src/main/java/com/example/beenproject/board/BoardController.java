package com.example.beenproject.board;

import com.example.beenproject.board.model.*;
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
import org.springframework.http.MediaType;
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

    @Operation(summary = "게시글 등록", description = "게시판에 게시글 등록")
    @Validated
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResVo postBoard(@RequestPart @NotNull(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
                               MultipartFile mainPic,
                           @RequestPart (required = false)
                           @Size(max = 4)
                           List<MultipartFile> pics,
                           @RequestPart InsBoardDto dto
                            ){
        dto.setMainPic(mainPic);
        dto.setPics(pics);
        return new ResVo(service.postBoard(dto));
    }

    @Operation(summary = "전체 게시글 목록", description = "게시글 전체(삭제/강제삭제 제외)")
    @Parameters(value = {
            @Parameter(name = "page", description = "1~30"),
            @Parameter(name = "search", description = "search(검색어)가 제공될 경우 해당 키워드가 포함된 게시글만 조회"),
            @Parameter(name = "type", description = "type 은 search 하는 조건임"),
            @Parameter(name = "sort", description = "sort:0 (default)최신순 /1 좋아요순/2 조회수 많은순")})
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
    @Operation(summary = "게시글 수정", description = "로그인 한 유저가 작성한 게시글 수정")
    @PutMapping
    public ResVo putBoard(@RequestPart(required = false)
                              MultipartFile mainPic,
                          @RequestPart (required = false)
                              @Size(max = 4)
                              List<MultipartFile> pics,
                          PutBoardDto dto){

        dto.setPics(pics);
        dto.setMainPic(mainPic);
        return new ResVo(service.putBoard(dto));
    }

    @Operation(summary = "게시글 삭제", description = "로그인 한 유저가 작성한 게시글 삭제 ")
    @DeleteMapping("/{iboard}")
    public ResVo delUserBoard(@PathVariable int iboard){
        return new ResVo(service.delUserBoard(iboard));
    }

    @Operation(summary = "게시글 좋아요", description = "좋아요 토글 ")
    @GetMapping("/like/{iboard}")
    public ResVo toggleLike(@PathVariable int iboard){

    }



}
