package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;



@RequiredArgsConstructor
@Component
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        try {
            String fileName = fileNameProvider.getTestFileName();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            CsvToBean<QuestionDto> csvReader = new CsvToBeanBuilder<QuestionDto>(new InputStreamReader(inputStream))
                    .withSkipLines(1)
                    .withType(QuestionDto.class)
                    .withSeparator(';')
                    .build();
            List<QuestionDto> questionDTOList = csvReader.parse();
            if (questionDTOList.isEmpty()) {
                throw new QuestionReadException("Questions is empty");
            }
            return questionDTOList.stream().map(QuestionDto::toDomainObject).toList();
        } catch (Exception e) {
            throw new QuestionReadException("Something wrong in questions read");
        }
    }
}
