package de.joscheffel.trainingsplan.exercises.variations;

import de.joscheffel.trainingsplan.exercises.ExerciseRepository;
import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationRequestDto;
import de.joscheffel.trainingsplan.exercises.variations.dtos.VariationResponseDto;
import de.joscheffel.trainingsplan.utils.Response;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class VariationServiceImpl implements VariationService {

  public static String ERROR_COULDNT_FIND_REFERENCED_EXERCISE = "Couldn't find referenced exercise";
  public static String ERROR_COULDNT_SUCCEED_THE_OPERATION = "Couldn't succeed the operation";
  private final VariationRepository variationRepository;
  private final VariationMapper variationMapper;
  private final ExerciseRepository exerciseRepository;

  public VariationServiceImpl(VariationRepository variationRepository,
      VariationMapper variationMapper, ExerciseRepository exerciseRepository) {
    this.variationRepository = variationRepository;
    this.variationMapper = variationMapper;
    this.exerciseRepository = exerciseRepository;
  }

  @Override
  public Response<VariationResponseDto> store(VariationRequestDto variationRequestDto) {
    var exerciseOptional = exerciseRepository.findById(variationRequestDto.exerciseId());
    if (exerciseOptional.isEmpty()) {
      return Response.error(ERROR_COULDNT_FIND_REFERENCED_EXERCISE);
    }
    var variation = variationMapper.mapVariationRequestDtoToVariation(variationRequestDto);
    variation.setExercise(exerciseOptional.get());
    var storedVariation = variationRepository.save(variation);
    if (variationRepository.existsById(storedVariation.getId())) {
      var variationResponseDto = variationMapper.mapVariationToVariationResponseDto(
          storedVariation);
      return Response.of(variationResponseDto);
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }

  @Override
  public Response<VariationResponseDto> show(String id) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      var variationOptional = variationRepository.findById(id);
      if (variationOptional.isPresent()) {
        var variationResponseDto = variationMapper.mapVariationToVariationResponseDto(
            variationOptional.get());
        return Response.of(variationResponseDto);
      }
      return Response.error("Not Found");
    }
    return Response.error("Bad Request");
  }

  @Override
  public Response<List<VariationResponseDto>> showAll() {
    var variations = variationRepository.findAll();
    var variationResponseDtos = variationMapper.mapVariationListToVariationResponseDtoList(
        variations);
    return Response.of(variationResponseDtos);
  }

  @Override
  public Response<VariationResponseDto> update(String id, VariationRequestDto variationRequestDto) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      if (variationRepository.existsById(id)) {
        var exerciseOptional = exerciseRepository.findById(variationRequestDto.exerciseId());
        if (exerciseOptional.isEmpty()) {
          return Response.error(ERROR_COULDNT_FIND_REFERENCED_EXERCISE);
        }
        var variation = variationMapper.mapVariationRequestDtoToVariation(variationRequestDto);
        variation.setExercise(exerciseOptional.get());
        variation.setId(id);
        var storedVariation = variationRepository.save(variation);
        var variationResponseDto = variationMapper.mapVariationToVariationResponseDto(
            storedVariation);
        return Response.of(variationResponseDto);
      }
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }

  @Override
  public Response<VariationResponseDto> deleteById(String id) {
    if (Objects.nonNull(id) && StringUtils.hasText(id)) {
      var variationOptional = variationRepository.findById(id);
      if (variationOptional.isPresent()) {
        variationRepository.deleteById(id);
        if (!variationRepository.existsById(id)) {
          var variationResponseDto = variationMapper.mapVariationToVariationResponseDto(
              variationOptional.get());
          return Response.of(variationResponseDto);
        }
      }
    }
    return Response.error(ERROR_COULDNT_SUCCEED_THE_OPERATION);
  }
}
