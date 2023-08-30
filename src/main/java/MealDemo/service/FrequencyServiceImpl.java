package MealDemo.service;


import MealDemo.dao.FrequencyRepository;
import MealDemo.entity.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FrequencyServiceImpl implements FrequencyService {

    private FrequencyRepository frequencyRepository;

    @Autowired
    public FrequencyServiceImpl(FrequencyRepository frequencyRepository) {
        this.frequencyRepository = frequencyRepository;
    }


    @Override
    public List<Frequency> findAll() {
        return frequencyRepository.findAll();
    }

    @Override
    public Optional<Frequency> findById(int theID) {
        return frequencyRepository.findById(theID);
    }

    @Override
    public Frequency save(Frequency frequency) {
        return frequencyRepository.save(frequency);
    }

    @Override
    public void deleteByID(int theID) {
        frequencyRepository.deleteById(theID);

    }

}
