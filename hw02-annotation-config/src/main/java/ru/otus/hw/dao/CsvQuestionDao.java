package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;



@RequiredArgsConstructor
@Component
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider; // подразумевается  @Autowired

    @Override
    public List<Question> findAll() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileNameProvider.getTestFileName());
        CsvToBean<QuestionDto> csvReader = new CsvToBeanBuilder<QuestionDto>(new InputStreamReader(inputStream))
                .withSkipLines(1)
                .withType(QuestionDto.class)
                .withSeparator(';')
                .build();
        return csvReader.parse().stream().map(QuestionDto::toDomainObject).toList();
    }
}
