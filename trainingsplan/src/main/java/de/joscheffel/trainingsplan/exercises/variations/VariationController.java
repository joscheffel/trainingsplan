package de.joscheffel.trainingsplan.exercises.variations;

import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationRequestDto;
import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationResponseDto;
import de.joscheffel.trainingsplan.generics.EntityRestController;
import de.joscheffel.trainingsplan.generics.EntityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/variations")
public class VariationController extends EntityRestController<VariationRequestDto, VariationResponseDto, String> {

  protected VariationController(
      VariationService variationService) {
    super(variationService);
  }
}
