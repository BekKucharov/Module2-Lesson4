package uz.pdp.springlesson4restsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.springlesson4restsecurity.entity.Card;
import uz.pdp.springlesson4restsecurity.entity.Income;
import uz.pdp.springlesson4restsecurity.entity.Outcome;
import uz.pdp.springlesson4restsecurity.payload.ApiResponse;
import uz.pdp.springlesson4restsecurity.payload.OutcomeDto;
import uz.pdp.springlesson4restsecurity.repository.CardRepository;
import uz.pdp.springlesson4restsecurity.repository.IncomeRepository;
import uz.pdp.springlesson4restsecurity.repository.OutcomeRepository;
import uz.pdp.springlesson4restsecurity.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OutcomeService {
    @Autowired
    OutcomeRepository outcomeRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    IncomeRepository incomeRepository;

    public ApiResponse add(OutcomeDto dto, HttpServletRequest request){
        Optional<Card> cardToId = cardRepository.findById(dto.getToCardId());
        if (!cardToId.isPresent()) return new ApiResponse("To card not found", false);
        Optional<Card> cardFromId = cardRepository.findById(dto.getFromCardId());
        if (!cardFromId.isPresent()) return new ApiResponse("From card not found", false);
        Card fromCard = cardFromId.get();
        Card toCard = cardToId.get();

        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);
        if (!userName.equals(fromCard.getUsername()))
            return new ApiResponse("This is not your card. Server Mistake", false);

        Double balance = fromCard.getBalance();
        Double totalBalance = dto.getAmount() + (dto.getAmount() / 100 + dto.getCommissionPercent());
        if (balance < totalBalance) return new ApiResponse("Balance is not enough", false);

        Outcome outcome = new Outcome();
        Income income = new Income();

        outcome.setAmount(dto.getAmount());
        outcome.setCommissionPercent(dto.getCommissionPercent());
        outcome.setFromCardId(fromCard);
        outcome.setToCardId(toCard);
        outcome.setDate(new Date());
        outcomeRepository.save(outcome);

        income.setAmount(dto.getAmount());
        income.setDate(new Date());
        income.setFromCardId(fromCard);
        income.setToCardId(toCard);
        incomeRepository.save(income);

        fromCard.setBalance(balance - totalBalance);
        toCard.setBalance(toCard.getBalance() + dto.getAmount());
        cardRepository.save(fromCard);
        cardRepository.save(toCard);
        return new ApiResponse("Transaction finished successfully", true);
    }

    public List<Outcome> getAll(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);
        List<Outcome> userOutcome = new ArrayList<>();

        List<Outcome> all = outcomeRepository.findAll();
        for (Outcome outcome : all) {
            if (outcome.getFromCardId().getUsername().equals(userName))
                userOutcome.add(outcome);
        }
        return userOutcome;
    }

    public ApiResponse getOne(Integer id, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);

        Optional<Outcome> outcomeId = outcomeRepository.findById(id);
        if (!outcomeId.isPresent()) return new ApiResponse("Outcome not found", false);
        if (!userName.equals(outcomeId.get().getFromCardId().getUsername()))
            return new ApiResponse("this card doesnt belongs to you", false);
        return new ApiResponse("", true, outcomeId.get());
    }
}
