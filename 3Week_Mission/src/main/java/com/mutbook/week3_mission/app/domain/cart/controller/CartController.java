package com.mutbook.week3_mission.app.domain.cart.controller;

import com.mutbook.week3_mission.app.base.rq.Rq;
import com.mutbook.week3_mission.app.domain.cart.entity.CartItem;
import com.mutbook.week3_mission.app.domain.cart.service.CartService;
import com.mutbook.week3_mission.app.domain.member.entity.Member;
import com.mutbook.week3_mission.app.domain.product.entity.Product;
import com.mutbook.week3_mission.app.domain.product.service.ProductService;
import com.mutbook.week3_mission.app.security.dto.MemberContext;
import com.mutbook.week3_mission.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final Rq rq;

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String showList(Model model) {
        List<CartItem> cartItems = cartService.getItemsByMember(rq.getMember());

        model.addAttribute("cartItems", cartItems);

        return "cart/list";
    }

    @PostMapping("/add/{id}")
    @PreAuthorize("isAuthenticated()")
    public String  addCartItem(@PathVariable Long id, @AuthenticationPrincipal MemberContext memberContext){
        Product product = productService.findById(id).get();
        cartService.addItem(memberContext.getMember(), product);
        return "redirect:/cart/list";
    }

    @PostMapping("/remove/{id}")
    @PreAuthorize("isAuthenticated()")
    public String removeItem(@PathVariable Long id) {
        cartService.removeItem(rq.getMember(), new Product((id)));

        return rq.redirectToBackWithMsg("장바구니에서 삭제되었습니다.");
    }


    @PostMapping("/removeItems")
    @PreAuthorize("isAuthenticated()")
    public String removeItems(String ids) {
        Member buyer = rq.getMember();

        String[] idsArr = ids.split(",");

        Arrays.stream(idsArr)
                .mapToLong(Long::parseLong)
                .forEach(id -> {
                    CartItem cartItem = cartService.findItemById(id).orElse(null);

                    if (cartService.actorCanDelete(buyer, cartItem)) {
                        cartService.removeItem(cartItem);
                    }
                });

        return "redirect:/cart/items?msg=" + Util.url.encode("%d건의 품목을 삭제하였습니다.".formatted(idsArr.length));
    }
}
