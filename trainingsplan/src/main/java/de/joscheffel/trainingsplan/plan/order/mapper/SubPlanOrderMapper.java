package de.joscheffel.trainingsplan.plan.order.mapper;

import de.joscheffel.trainingsplan.plan.model.Plan;
import de.joscheffel.trainingsplan.plan.order.dto.SubPlanOrderDto;
import de.joscheffel.trainingsplan.plan.order.model.SubPlanOrder;
import java.util.List;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubPlanOrderMapper {

  SubPlanOrder mapSubPlanOrderRequestToSubPlanOrder(SubPlanOrderDto subPlanOrderDto);


  @Mapping(target = "planId", source = "subPlanOrder.plan")
  SubPlanOrderDto mapSubPlanOrderToSubPlanOrderResponseDto(SubPlanOrder subPlanOrder);

  default String mapPlanToPlanIdString(Plan plan) {
    return Objects.nonNull(plan) ? plan.getId() : null;
  }

  List<SubPlanOrder> mapSubPlanOrderRequestDtosToSubPlanOrders(
      List<SubPlanOrderDto> subPlanOrderDtos);

  List<SubPlanOrderDto> mapSubPlanOrdersToSubPlanOrderDtos(List<SubPlanOrder> subPlanOrders);
}
