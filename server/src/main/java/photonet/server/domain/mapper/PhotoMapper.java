package photonet.server.domain.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import photonet.server.domain.entity.Photo;
import photonet.server.domain.service.upload.FileService;


@Mapper
public abstract class PhotoMapper {

    @Autowired
    @Qualifier("CloudService")
    private FileService fileService;

    public byte[] getPhotoBlob(Photo photo) {
        return photo == null ? null : fileService.getBlob(photo.getPath());
    }

}
