package com.ae.chaebbiSpring.api;

import com.ae.chaebbiSpring.config.BaseException;
import com.ae.chaebbiSpring.config.BaseResponse;
import com.ae.chaebbiSpring.domain.Record;
import com.ae.chaebbiSpring.domain.User;
import com.ae.chaebbiSpring.dto.request.DateRecordRequestDto;
import com.ae.chaebbiSpring.dto.request.DetailRecordRequestDto;
import com.ae.chaebbiSpring.dto.response.*;
import com.ae.chaebbiSpring.service.RecordService;
import com.ae.chaebbiSpring.service.UserService;
import com.ae.chaebbiSpring.aws.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.ae.chaebbiSpring.config.BaseResponseStatus.*;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class RecordApiController {
    private final RecordService recordService;
    private final UserService userService;
    private final S3Uploader s3Uploader;

    //1-1
    @PostMapping(value = "/api/record", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<RecordResponseDto> createRecord(@AuthenticationPrincipal String userId,
                                                        @RequestParam (value = "image", required = false) MultipartFile multipartFile,
                                                        @RequestParam (value = "text", required = true) String text,
                                                        @RequestParam (value = "calory", required = false) String calory,
                                                        @RequestParam (value = "carb", required = false) String carb,
                                                        @RequestParam (value = "protein", required = false) String protein,
                                                        @RequestParam (value = "fat", required = false) String fat,
                                                        @RequestParam (value = "rdate", required = true) String rdate,
                                                        @RequestParam (value = "rtime", required = true) String rtime,
                                                        @RequestParam (value = "amount", required = false) Double amount,
                                                        @RequestParam (value = "meal", required = true) int meal
                                             ) throws IOException {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));
        //S3 Bucket upload
        String img_url = "";
        if(multipartFile != null) {
            if(!multipartFile.isEmpty()) {
                img_url = s3Uploader.upload(multipartFile, "static");
            }
        }

        if(multipartFile == null || multipartFile.isEmpty()) {
            return new BaseResponse<>(POST_RECORD_NO_IMAGE);
        }

        if(text.isEmpty() || text.equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_TEXT);
        }

        if(calory.isEmpty() || calory.equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_CALORY);
        }

        if(Integer.valueOf(calory) <= 0) {
            return new BaseResponse<>(POST_RECORD_MINUS_CALORY);
        }

        if(carb.isEmpty() || carb.equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_CARB);
        }

        if(Integer.valueOf(carb) <= 0) {
            return new BaseResponse<>(POST_RECORD_MINUS_CARB);
        }

        if(protein.isEmpty() || protein.equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_PROTEIN);
        }

        if(Integer.valueOf(protein) <= 0) {
            return new BaseResponse<>(POST_RECORD_MINUS_PROTEIN);
        }

        if(fat.isEmpty() || fat.equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_FAT);
        }

        if(Integer.valueOf(fat) <= 0) {
            return new BaseResponse<>(POST_RECORD_MINUS_FAT);
        }

        if(rdate.isEmpty() || rdate.equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_RDATE);
        }

        try {
            LocalDate.from(LocalDate.parse(rdate, DateTimeFormatter.ofPattern("yyyy.MM.dd.")));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return new BaseResponse<>(POST_RECORD_INVALID_RDATE);
        }

        if(rtime.isEmpty() || rtime.equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_RTIME);
        }

        try{
            LocalTime.from(LocalTime.parse(rtime, DateTimeFormatter.ofPattern("HH:mm")));
        }catch (DateTimeParseException e) {
            e.printStackTrace();
            return new BaseResponse<>(POST_RECORD_INVALID_RTIME);
        }


        if(amount == null) {
            return new BaseResponse<>(POST_RECORD_NO_AMOUNT);
        }

        if(amount <= 0) {
            return new BaseResponse<>(POST_RECORD_MINUS_AMOUNT);
        }

        if(String.valueOf(meal).isEmpty() || String.valueOf(meal).equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_MEAL);
        }

        if(meal != 0 && meal != 1 && meal != 2) {
            return new BaseResponse<>(POST_RECORD_INVALID_MEAL);
        }


        Record record = Record.createRecord(img_url, text, date, calory, carb, protein, fat,
                rdate, rtime, amount, meal, user);
        Long id = recordService.record(record);

        return new BaseResponse<>(new RecordResponseDto(id.intValue()));
    }

    //1-2
    @PostMapping("/api/daterecord")
    public BaseResponse<DateRecordResponseDto> dateRecords(@AuthenticationPrincipal String userId, @RequestBody @Valid DateRecordRequestDto request) {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }


        if(request.getDate().isEmpty()) {
            return new BaseResponse<>(POST_RECORD_NO_RDATE);
        }

        if(request.getDate().equals("")){
            return new BaseResponse<>(POST_RECORD_NO_RDATE);
        }

        try {
            LocalDate.from(LocalDate.parse(request.getDate(), DateTimeFormatter.ofPattern("yyyy.MM.dd.")));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return new BaseResponse<>(POST_RECORD_INVALID_RDATE);
        }

        List<Record> findRecords = recordService.findDateRecords(Long.valueOf(userId), request.getDate());
        List<DateRecordDto> bRecords = new ArrayList<DateRecordDto>();
        List<DateRecordDto> lRecords = new ArrayList<DateRecordDto>();
        List<DateRecordDto> dRecords = new ArrayList<DateRecordDto>();
        Double bCal, lCal, dCal, totalCalory, totalCarb, totalPro, totalFat;
        bCal = lCal =  dCal = totalCalory = totalCarb = totalPro = totalFat = 0D;

        for(Record record: findRecords) {
            if(record.getMeal() == 0) {
                bCal += Double.parseDouble(record.getCal());
                bRecords.add(new DateRecordDto(record.getId().intValue(), record.getText(), record.getServer_date(), record.getCal(), record.getCarb(), record.getProtein(),
                        record.getFat(), record.getDate(), record.getTime(), record.getAmount()));
            } else if(record.getMeal() == 1) {
                lCal += Double.parseDouble(record.getCal());
                lRecords.add(new DateRecordDto(record.getId().intValue(), record.getText(), record.getServer_date(), record.getCal(), record.getCarb(), record.getProtein(),
                        record.getFat(), record.getDate(), record.getTime(), record.getAmount()));
            } else if(record.getMeal() == 2) {
                dCal += Double.parseDouble(record.getCal());
                dRecords.add(new DateRecordDto(record.getId().intValue(), record.getText(), record.getServer_date(), record.getCal(), record.getCarb(), record.getProtein(),
                        record.getFat(), record.getDate(), record.getTime(), record.getAmount()));
            }
            totalCalory += Double.parseDouble(record.getCal());
            totalCarb += Double.parseDouble(record.getCarb());
            totalPro += Double.parseDouble(record.getProtein());
            totalFat += Double.parseDouble(record.getFat());
        }
        RecordsDto b = new RecordsDto(0, bCal.intValue(), bRecords);
        RecordsDto l = new RecordsDto(1, lCal.intValue(), lRecords);
        RecordsDto d = new RecordsDto(2, dCal.intValue(), dRecords);
        List<RecordsDto> records = new ArrayList<RecordsDto>();
        records.add(b); records.add(l); records.add(d);



        return new BaseResponse<>(new DateRecordResponseDto(totalCalory.intValue(), totalCarb.intValue(), totalPro.intValue(), totalFat.intValue(),
                (int) Math.round(Double.parseDouble(user.getRcal())), Integer.parseInt(user.getRcarb()), Integer.parseInt(user.getRpro()),
                (int) Math.round(Double.parseDouble(user.getRfat())),
                records));
    }

    //1-3
    @PostMapping("api/detailrecord")
    public BaseResponse<ResultResponse> recordResponse(@AuthenticationPrincipal String userId, @RequestBody @Valid DetailRecordRequestDto request) throws BaseException {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }


        List<Record> findDetailRecord = recordService.findDetailOne(Long.valueOf(userId), Long.valueOf(request.getRecord_id()));
        String name = userService.findOne(Long.valueOf(userId)).getName();
        List<DetailRecordResponseDto> collect = findDetailRecord.stream()
                .map(m -> new DetailRecordResponseDto(name, m.getText(), m.getCal(), m.getCarb(), m.getProtein(), m.getFat(), m.getImage_url(), m.getDate(), m.getTime(), m.getAmount()))
                .collect(toList());
        return new BaseResponse<>(new ResultResponse(collect));

    }

    //1-4
    @PostMapping("api/record-no-img")
    public BaseResponse<RecordResponseDto> createRecord(@AuthenticationPrincipal String userId,
                                          @RequestParam (value = "text", required = true) String text,
                                          @RequestParam (value = "calory", required = false) String calory,
                                          @RequestParam (value = "carb", required = false) String carb,
                                          @RequestParam (value = "protein", required = false) String protein,
                                          @RequestParam (value = "fat", required = false) String fat,
                                          @RequestParam (value = "rdate", required = true) String rdate,
                                          @RequestParam (value = "rtime", required = true) String rtime,
                                          @RequestParam (value = "amount", required = false) Double amount,
                                          @RequestParam (value = "meal", required = true) int meal
    ) throws IOException {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));

        if(text.isEmpty() || text.equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_TEXT);
        }

        if(calory.isEmpty() || calory.equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_CALORY);
        }

        if(Integer.valueOf(calory) <= 0) {
            return new BaseResponse<>(POST_RECORD_MINUS_CALORY);
        }

        if(carb.isEmpty() || carb.equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_CARB);
        }

        if(Integer.valueOf(carb) <= 0) {
            return new BaseResponse<>(POST_RECORD_MINUS_CARB);
        }

        if(protein.isEmpty() || protein.equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_PROTEIN);
        }

        if(Integer.valueOf(protein) <= 0) {
            return new BaseResponse<>(POST_RECORD_MINUS_PROTEIN);
        }

        if(fat.isEmpty() || fat.equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_FAT);
        }

        if(Integer.valueOf(fat) <= 0) {
            return new BaseResponse<>(POST_RECORD_MINUS_FAT);
        }

        if(rdate.isEmpty() || rdate.equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_RDATE);
        }

        try {
            LocalDate.from(LocalDate.parse(rdate, DateTimeFormatter.ofPattern("yyyy.MM.dd.")));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return new BaseResponse<>(POST_RECORD_INVALID_RDATE);
        }

        if(rtime.isEmpty() || rtime.equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_RTIME);
        }

        try{
            LocalTime.from(LocalTime.parse(rtime, DateTimeFormatter.ofPattern("HH:mm")));
        }catch (DateTimeParseException e) {
            e.printStackTrace();
            return new BaseResponse<>(POST_RECORD_INVALID_RTIME);
        }



        if(amount == null) {
            return new BaseResponse<>(POST_RECORD_NO_AMOUNT);
        }

        if(amount.intValue() <= 0) {
            return new BaseResponse<>(POST_RECORD_MINUS_AMOUNT);
        }

        if(String.valueOf(meal).isEmpty() || String.valueOf(meal).equals("")) {
            return new BaseResponse<>(POST_RECORD_NO_MEAL);
        }

        if(meal != 0 && meal != 1 && meal != 2) {
            return new BaseResponse<>(POST_RECORD_INVALID_MEAL);
        }

        Record record = Record.createRecord(null, text, date, calory, carb, protein, fat, rdate, rtime, amount, meal, user);
        Long id = recordService.record(record);

        return new BaseResponse<>(new RecordResponseDto(id.intValue()));
    }

}
