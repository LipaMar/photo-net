package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import photonet.server.domain.entity.Category;
import photonet.server.domain.entity.Post;
import photonet.server.domain.entity.User;
import photonet.server.webui.profile.dto.ProfileDto;
import photonet.server.webui.dto.discover.PhotographerBasicDto;

import java.util.List;

@Mapper(uses = {RateMapper.class, UserMapper.class, PhotoMapper.class, PostMapper.class, CommentMapper.class})
public interface PhotographerMapper {



}
