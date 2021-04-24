package uz.pdp.springlesson4restsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.springlesson4restsecurity.entity.Income;
import uz.pdp.springlesson4restsecurity.payload.ApiResponse;
import uz.pdp.springlesson4restsecurity.repository.CardRepository;
import uz.pdp.springlesson4restsecurity.repository.IncomeRepository;
import uz.pdp.springlesson4restsecurity.repository.OutcomeRepository;
import uz.pdp.springlesson4restsecurity.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    IncomeRepository incomeRepository;

    public List<Income> getAll(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);
        List<Income> userIncome = new ArrayList<>();

        List<Income> all = incomeRepository.findAll();
        for (Income income : all) {
            if (income.getToCardId().getUsername().equals(userName))
                userIncome.add(income);
        }
        return userIncome;
    }

    public ApiResponse getOne(Integer id, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);
        Optional<Income> incomeId = incomeRepository.findById(id);
        if (!incomeId.isPresent()) return new ApiResponse("Income transaction not found", false);
        if (!userName.equals(incomeId.get().getToCardId().getUsername()))
            return new ApiResponse("Income transaction NOT belongs to you. Server Mistake", false);
        return new ApiResponse("", true, incomeId.get());
    }
}
