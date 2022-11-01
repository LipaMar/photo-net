package photonet.server.domain.service.upload.impl;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gcp.core.GcpProjectIdProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import photonet.server.core.utils.FileUtils;
import photonet.server.domain.service.upload.FileService;

@Service
@Slf4j
@Profile("cloud")
@Primary
@RequiredArgsConstructor
class CloudFileService implements FileService {

  private final String BUCKET_NAME = "photo-net";
  private final GcpProjectIdProvider projectIdProvider;
  private final Storage storage;
  private final String separator = "/";

  @Override
  public byte[] getBlob(String url) {
    return storage.get(BlobId.of(BUCKET_NAME, url)).getContent();
  }

  @Override
  public String getUrl(String url) {
    return String.format("https://storage.googleapis.com/%s/%s", BUCKET_NAME, url);
  }

  @Override
  public String saveFile(byte[] file) {
    return uploadObject(projectIdProvider.getProjectId(), BUCKET_NAME, FileUtils.generatePath(DIR),
        file);
  }

  private String uploadObject(String projectId, String bucketName, String objectName, byte[] file) {
    var path = FileUtils.generatePath(DIR, separator);
    BlobId blobId = BlobId.of(bucketName, path);
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
    storage.create(blobInfo, file);

    log.info("File uploaded to bucket {} as {}", bucketName, path);
    return path;
  }
}
