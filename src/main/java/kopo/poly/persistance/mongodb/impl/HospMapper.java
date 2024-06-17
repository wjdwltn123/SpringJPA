package kopo.poly.persistance.mongodb.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import kopo.poly.dto.HospSearchDTO;
import kopo.poly.persistance.mongodb.AbstractMongoDBcomon;
import kopo.poly.persistance.mongodb.IHospMapper;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor

public class HospMapper extends AbstractMongoDBcomon implements IHospMapper {

    private final MongoTemplate mongodb;
    @Override
    public List<HospSearchDTO> getHospInfo(String colNm, HospSearchDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".HospSearch Start!");


        // 조회 결과를 전달하기 위한 객체 생성
        List<HospSearchDTO> rList = new LinkedList<>();

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        String business = CmmUtil.nvl(pDTO.businessType());

        // 조회할 조건( select 해오기)
        Document query = new Document();
        query.append("BusinessType", business );

        log.info("businessType : " + business);

        Document projection = new Document();
        projection.append("businessName", "$BusinessName");
        projection.append("businessType", "$BusinessType");
        projection.append("lotAddress", "$LotAddress");
        projection.append("coordinateX", "$CoordinateX");
        projection.append("coordinateY", "$CoordinateY");
        projection.append("businessStatusName", "$BusinessStatusName");

        projection.append("_id", 0);

        FindIterable<Document> rs = col.find(query).projection(projection);

        for (Document doc : rs) {
            String businessName = CmmUtil.nvl(doc.getString("businessName"));
            String businessType = CmmUtil.nvl(doc.getString("businessType"));
            String lotAddress = CmmUtil.nvl(doc.getString("lotAddress"));
            String coordinateX = CmmUtil.nvl(doc.getString("coordinateX"));
            String coordinateY = CmmUtil.nvl(doc.getString("coordinateY"));
            String businessStatusName = CmmUtil.nvl(doc.getString("businessStatusName"));

            HospSearchDTO rDTO = HospSearchDTO.builder()
                    .businessName(businessName)
                    .businessType(businessType)
                    .lotAddress(lotAddress)
                    .coordinateX(coordinateX)
                    .coordinateY(coordinateY)
                    .businessStatusName(businessStatusName)
                    .build();

            log.info("businessName : " + businessName);
            log.info("coordinateX : " + coordinateX);
            log.info("coordinateY : " + coordinateY);

            rList.add(rDTO);
        }

        log.info(this.getClass().getName() +  " HospSearch End!");

        return rList;
    }


}

