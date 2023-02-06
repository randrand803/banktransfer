package com.bank.controller;
import com.bank.model.TransferRecord;
import com.bank.util.ExchangeUtil;
import com.bank.util.KafkaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;


@Api("Bank transactions APIs")
@RestController
@RequestMapping("/data")
public class TransferController {

    @ApiOperation("Obtain transaction records of a certain month")
    @GetMapping("/getTransferRecords")
    public List<TransferRecord> getTransferRecord(@RequestParam int userid, @RequestParam int month) throws Exception {
        //Begin to obtain transaction data
        List<TransferRecord> transferRecordList = new ArrayList<TransferRecord>();
        //Consuming records from Kafka
        List<String> result = KafkaUtil.consumerFunction();
        for (String item : result) {
            String[] value = item.split(",");
            //Match the user ID and specific month
            if (Integer.valueOf(value[1]) == userid && value[6].indexOf(month < 10 ? "-0" + month + "-" : "-" + month + "-") >= 0) {
                TransferRecord record = new TransferRecord(Integer.valueOf(value[0]), Integer.valueOf(value[1]), Double.valueOf(value[2]), value[3], Double.valueOf(value[4]), value[5], value[6], "");
                transferRecordList.add(record);
            }
        }
        transferRecordList.sort((a, b) -> a.getTransferTime().compareTo(b.getTransferTime()));
        return transferRecordList;
    }


    @ApiOperation("Generate 50000 random transactions records")
    @GetMapping("/createTrans")
    public void createTrans() {
        //Produce random data
        KafkaUtil.ProducerFunction(ExchangeUtil.createRecord());
        System.out.println("Finished the generation of records======================================");
    }

    @ApiOperation("hello")
    @GetMapping("/Hello~")
    public String Hello(){
        return "Hello";
    }

}
