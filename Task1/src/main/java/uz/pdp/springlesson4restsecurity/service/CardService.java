package uz.pdp.springlesson4restsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.springlesson4restsecurity.entity.Card;
import uz.pdp.springlesson4restsecurity.payload.ApiResponse;
import uz.pdp.springlesson4restsecurity.payload.CardDto;
import uz.pdp.springlesson4restsecurity.repository.CardRepository;
import uz.pdp.springlesson4restsecurity.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    JwtProvider jwtProvider;


    public Card getCard(Integer id, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        token= token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);
        Optional<Card> cardId = cardRepository.findById(id);
        if (!cardId.isPresent()) return null;
        if (!userName.equals(cardId.get().getUsername())) return null;
        return cardId.get();
    }

    public List<Card> getCard(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        token= token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);
        List<Card> userCards = new ArrayList<>();

        List<Card> all = cardRepository.findAll();
        for (Card card : all) {
            if (card.getUsername().equals(userName))
                userCards.add(card);
        }
        return userCards;
    }

    public ApiResponse add(CardDto dto, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);
        Card card = new Card();
        card.setUsername(userName);
        card.setCardNumber(dto.getCardNumber());
        card.setExpireDate(dto.getExpiredDate());
        cardRepository.save(card);
        return new ApiResponse("New Card added", true);
    }

    public ApiResponse edit(Integer id, CardDto dto, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        token= token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);
        Optional<Card> cardId = cardRepository.findById(id);
        if (!cardId.isPresent()) return new ApiResponse("Card not found", false);
        if (!userName.equals(cardId.get().getUsername()))
            return new ApiResponse("This card doesnt belong to you", false);
        Card card = cardId.get();
        card.setUsername(userName);
        card.setCardNumber(dto.getCardNumber());
        card.setExpireDate(dto.getExpiredDate());
        cardRepository.save(card);
        return new ApiResponse("Card info edited", true);
    }

    public ApiResponse delete(Integer id, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        token= token.substring(7);
        String userName = jwtProvider.getUserNameFromToken(token);
        Optional<Card> cardId = cardRepository.findById(id);
        if (!cardId.isPresent()) return new ApiResponse("Card not found", false);
        if (!userName.equals(cardId.get().getUsername()))
            return new ApiResponse("This card doesnt belong to you", false);
        cardRepository.deleteById(id);
        return new ApiResponse("Card deleted", true);
    }




}
