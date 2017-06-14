package com.minimerce.core.component.order;

import com.google.common.collect.Lists;
import com.minimerce.core.component.deal.SaleDealReader;
import com.minimerce.core.domain.deal.option.Option;
import com.minimerce.core.domain.order.option.OrderOption;
import com.minimerce.core.support.exception.MinimerceException;
import com.minimerce.core.support.object.order.OrderRequestDetail;
import com.minimerce.core.support.object.response.ErrorCode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by gemini on 01/04/2017.
 */
@Component
public class OrderOptionMaker {
    private final SaleDealReader saleDealReader;
    private final OrderOptionGenerator orderOptionGenerator;

    @Inject
    public OrderOptionMaker(SaleDealReader saleDealReader, OrderOptionGenerator orderOptionGenerator) {
        this.saleDealReader = saleDealReader;
        this.orderOptionGenerator = orderOptionGenerator;
    }

    public List<OrderOption> make(Long clientId, List<OrderRequestDetail> requestDetails) {
        List<OrderOption> orders = Lists.newArrayList();
        for(OrderRequestDetail each : requestDetails) {
            Option option = saleDealReader.findBySaleOption(each.getOptionId());

            OrderOption orderOption = orderOptionGenerator.generate(clientId, option);
            if(each.getUnitPrice() != orderOption.getSalePrice()) throw new MinimerceException(ErrorCode.NOT_EQUAL_UNIT_PRICE);
            if(each.getPrice() != orderOption.getSalePrice() * each.getQuantity()) throw new MinimerceException(ErrorCode.NOT_EQUAL_UNIT_PRICE);

            for (int i = 0; i < each.getQuantity(); i++) {
                orders.add(ObjectUtils.clone(orderOption));
            }
        }
        return orders;
    }
}
