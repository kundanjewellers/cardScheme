package com.kundan.CardScheme.service.serviceImpl;

import com.kundan.CardScheme.dto.CardRequestDto;
import com.kundan.CardScheme.dto.CardResponseDto;
import com.kundan.CardScheme.dto.CardViewResponseDto;
import com.kundan.CardScheme.dto.UpdateCardResponseDto;
import com.kundan.CardScheme.entity.BatchEntity;
import com.kundan.CardScheme.entity.CardEntity;
import com.kundan.CardScheme.entity.PaymentEntity;
import com.kundan.CardScheme.exception.NotFoundException;
import com.kundan.CardScheme.repository.BatchRepository;
import com.kundan.CardScheme.repository.CardRepository;
import com.kundan.CardScheme.repository.PaymentRepository;
import com.kundan.CardScheme.service.CardService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public String createBatch(String batchName, int batchSize) {
        BatchEntity batch = batchRepository.findByBatchName(batchName);

        if (batch != null && batch.isCompleted()) {
            return "batch is already available and completed";
        }
        if (batch != null) {
            return "batch already exist";
        }
        if (!ObjectUtils.isEmpty(batchName) && batchSize > 0) {
            BatchEntity batchEntityBuilder = BatchEntity.builder()
                    .batchName(batchName)
                    .batchSize(batchSize)
                    .createdAt(new Date())
                    .build();
            batchRepository.save(batchEntityBuilder);
            return "batch created successfully";
        } else return "batch creation failed";
    }

    @Override
    public CardResponseDto createCard(CardRequestDto requestDto) {
        String[] nameArray = requestDto.getCustomerName().split(",");
        List<String> nameList = Arrays.asList(nameArray);
        CardResponseDto responseDto = new CardResponseDto();
        if (ObjectUtils.isEmpty(requestDto.getCustomerName()) ||
                requestDto.getMobileNumber() <= 0 ||
                requestDto.getAmount() <= 0) {
            responseDto.setMessage("fill all fields");
            return responseDto;
        }
        List<String> cardIds = new ArrayList<>();
        int groupId = cardRepository.findCurrentGroupId() != null ? cardRepository.findCurrentGroupId() : 0;

        BatchEntity batchEntity = batchRepository.findByBatchName(requestDto.getBatchName());
        if (batchEntity != null) {
            if (!batchEntity.isCompleted()) {
                for (String customerName : nameList) {

                    CardEntity cardEntity = new CardEntity();

                    int i = batchEntity.getCounter() + 1;
                    cardEntity.setCardId(requestDto.getBatchName() + "-" + i);
                    cardEntity.setCustomerName(customerName);
                    cardEntity.setBatchName(requestDto.getBatchName());
                    cardEntity.setAmount(requestDto.getAmount());
                    cardEntity.setMobileNumber(requestDto.getMobileNumber());
                    cardEntity.setCreatedAt(new Date());
                    if (nameList.size() > 1) {
                        cardEntity.setGroupId(groupId + 1);
                        responseDto.setGroupId(groupId + 1);
                    }
                    cardRepository.save(cardEntity);
                    batchEntity.setCounter(i);
                    if (i == batchEntity.getBatchSize()) {
                        batchEntity.setCompleted(true);
                    }
                    batchRepository.save(batchEntity);
                    cardIds.add(requestDto.getBatchName() + "-" + i);
                }
                responseDto.setCardIds(cardIds);
                responseDto.setMessage("cards created successfully");
            } else {
                responseDto.setMessage("batch limit reached");
            }
        } else {
            responseDto.setMessage("batch not found");
        }
        return responseDto;
    }

    @Override
    public Map<String, List<String>> getCardDetails(String cardNumber) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyy");
        Map<String, List<String>> customResponse = new LinkedHashMap<>();
        CardEntity response = cardRepository.findByCardId(cardNumber);
        if (response == null) {
            customResponse.put("message", List.of("card not found"));
            return customResponse;
        }
        if (response.getFirst() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "first");
            customResponse.put("1st", Arrays.asList(simpleDateFormat.format(response.getFirst()), paymentEntity.getPaymentType()));
        }
        if (response.getSecond() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "second");
            customResponse.put("2nd", Arrays.asList(simpleDateFormat.format(response.getSecond()), paymentEntity.getPaymentType()));
        }
        if (response.getThird() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "third");
            customResponse.put("3rd", Arrays.asList(simpleDateFormat.format(response.getThird()), paymentEntity.getPaymentType()));
        }
        if (response.getFourth() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "fourth");
            customResponse.put("4th", Arrays.asList(simpleDateFormat.format(response.getFourth()), paymentEntity.getPaymentType()));
        }
        if (response.getFifth() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "fifth");
            customResponse.put("5th", Arrays.asList(simpleDateFormat.format(response.getFifth()), paymentEntity.getPaymentType()));
        }
        if (response.getSixth() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "sixth");
            customResponse.put("6th", Arrays.asList(simpleDateFormat.format(response.getSixth()), paymentEntity.getPaymentType()));
        }
        if (response.getSeventh() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "seventh");
            customResponse.put("7th", Arrays.asList(simpleDateFormat.format(response.getSeventh()), paymentEntity.getPaymentType()));
        }
        if (response.getEighth() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "eighth");
            customResponse.put("8th", Arrays.asList(simpleDateFormat.format(response.getEighth()), paymentEntity.getPaymentType()));
        }
        if (response.getNinth() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "ninth");
            customResponse.put("9th", Arrays.asList(simpleDateFormat.format(response.getNinth()), paymentEntity.getPaymentType()));
        }
        if (response.getTenth() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "tenth");
            customResponse.put("10th", Arrays.asList(simpleDateFormat.format(response.getTenth()), paymentEntity.getPaymentType()));
        }
        if (response.getEleventh() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "eleventh");
            customResponse.put("11th", Arrays.asList(simpleDateFormat.format(response.getEleventh()), paymentEntity.getPaymentType()));
        }
        if (response.getTwelfth() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "twelfth");
            customResponse.put("12th", Arrays.asList(simpleDateFormat.format(response.getTwelfth()), paymentEntity.getPaymentType()));
        }
        if (response.getThirteen() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "thirteen");
            customResponse.put("13th", Arrays.asList(simpleDateFormat.format(response.getThirteen()), paymentEntity.getPaymentType()));
        }
        if (response.getFourteen() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "fourteen");
            customResponse.put("14th", Arrays.asList(simpleDateFormat.format(response.getFourteen()), paymentEntity.getPaymentType()));
        }
        if (response.getFifteenth() != null) {
            PaymentEntity paymentEntity = paymentRepository.findPaymentTypeByCardIdAndMonth(response.getCardId(), "fifteenth");
            customResponse.put("15th", Arrays.asList(simpleDateFormat.format(response.getFifteenth()), paymentEntity.getPaymentType()));
        }

        if (customResponse.isEmpty()) {
            customResponse.put("message", List.of("update 1st month"));
        }

        return customResponse;
    }


    @Override
    public List<String> getActiveBatch() {
        List<BatchEntity> batchEntity = batchRepository.findByIsCompletedFalse();
        return batchEntity.stream().map(BatchEntity::getBatchName).collect(Collectors.toList());
    }

    @Override
    public Integer getAvailableBatchCount(String batch) {
        BatchEntity response = batchRepository.findByBatchName(batch);
        if (response == null) {
            return null;
        }
        return response.getBatchSize() - response.getCounter();
    }

    @Override
    @Transactional
    public String updateCard(String cardNumber, int month, String dateString, boolean allowMultiple, String paymentType) throws ParseException {
        Date date = new Date();
        if (!ObjectUtils.isEmpty(dateString)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.parse(dateString);
        }

        CardEntity cardEntity = cardRepository.findByCardId(cardNumber);

        if (cardEntity == null) {
            return "card not found";
        }

        if (cardEntity.isDraw()){
           return "Draw card cannot be updated";
        }

        String fieldName = getFieldName(month);
        Date monthPaid = isMonthPaid(cardNumber, fieldName);
        if (monthPaid != null) {
            return month + " month already paid";
        }

        if (!fieldName.equals("first")) {
            String field = getFieldName(month - 1);
            Date checkPreviousMonth = isMonthPaid(cardNumber, field);
            if (checkPreviousMonth == null) {
                return "previous month pending";
            }
        }

        int updatedCount = updateCardInRepository(cardNumber, date, fieldName);
        if (updatedCount > 0) {
            PaymentEntity paymentEntity = PaymentEntity.builder()
                    .cardId(cardEntity.getCardId())
                    .paymentType(paymentType)
                    .month(fieldName)
                    .amount(cardEntity.getAmount())
                    .createdAt(date)
                    .batch(cardEntity.getBatchName())
                    .build();
            paymentRepository.save(paymentEntity);

            return month + " month updated";
        }

        return "card not updated";
    }

    @Modifying
    private int updateCardInRepository(String cardNumber, Date date, String fieldName) {
        String query = "update card_entity set " + fieldName + " = :date where card_id = :cardNumber";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("date", date);
        nativeQuery.setParameter("cardNumber", cardNumber);
        return nativeQuery.executeUpdate();
    }

    private static String getFieldName(int month) {
        Map<Integer, String> dateFieldMap = new HashMap<>();
        dateFieldMap.put(1, "first");
        dateFieldMap.put(2, "second");
        dateFieldMap.put(3, "third");
        dateFieldMap.put(4, "fourth");
        dateFieldMap.put(5, "fifth");
        dateFieldMap.put(6, "sixth");
        dateFieldMap.put(7, "seventh");
        dateFieldMap.put(8, "eighth");
        dateFieldMap.put(9, "ninth");
        dateFieldMap.put(10, "tenth");
        dateFieldMap.put(11, "eleventh");
        dateFieldMap.put(12, "twelfth");
        dateFieldMap.put(13, "thirteen");
        dateFieldMap.put(14, "fourteen");
        dateFieldMap.put(15, "fifteenth");

        // Ensure the map contains the specified month
        if (!dateFieldMap.containsKey(month)) {
            throw new IllegalArgumentException("Invalid month specified.");
        }

        // Get the corresponding field name
        String fieldName = dateFieldMap.get(month);
        return fieldName;
    }

    public Date isMonthPaid(String cardNumber, String fieldName) {

        String query = "select " + fieldName + " from card_entity where card_id = :cardNumber";

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("cardNumber", cardNumber);

        Object result = nativeQuery.getSingleResult();
        return (Date) result;

    }

    @Override
    public boolean isDraw(String cardNumber) {
        CardEntity entity = cardRepository.findByCardId(cardNumber);
        if (entity==null){
            return false;
        }
        return entity.isDraw();
    }
}


