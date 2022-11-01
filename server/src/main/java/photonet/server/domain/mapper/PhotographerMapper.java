package photonet.server.domain.mapper;

import org.mapstruct.Mapper;

@Mapper(uses = {RateMapper.class, UserMapper.class, PhotoMapper.class, PostMapper.class,
    CommentMapper.class})
public interface PhotographerMapper {


}
