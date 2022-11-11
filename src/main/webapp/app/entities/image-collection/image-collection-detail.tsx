import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './image-collection.reducer';

export const ImageCollectionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const imageCollectionEntity = useAppSelector(state => state.imageCollection.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="imageCollectionDetailsHeading">
          <Translate contentKey="rfidMongodbApp.imageCollection.detail.title">ImageCollection</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{imageCollectionEntity.id}</dd>
          <dt>
            <span id="guid">
              <Translate contentKey="rfidMongodbApp.imageCollection.guid">Guid</Translate>
            </span>
          </dt>
          <dd>{imageCollectionEntity.guid}</dd>
          <dt>
            <span id="cmd">
              <Translate contentKey="rfidMongodbApp.imageCollection.cmd">Cmd</Translate>
            </span>
          </dt>
          <dd>{imageCollectionEntity.cmd}</dd>
          <dt>
            <span id="base64">
              <Translate contentKey="rfidMongodbApp.imageCollection.base64">Base 64</Translate>
            </span>
          </dt>
          <dd>
            {imageCollectionEntity.base64 ? (
              <div>
                {imageCollectionEntity.base64ContentType ? (
                  <a onClick={openFile(imageCollectionEntity.base64ContentType, imageCollectionEntity.base64)}>
                    <img
                      src={`data:${imageCollectionEntity.base64ContentType};base64,${imageCollectionEntity.base64}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {imageCollectionEntity.base64ContentType}, {byteSize(imageCollectionEntity.base64)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="gantry">
              <Translate contentKey="rfidMongodbApp.imageCollection.gantry">Gantry</Translate>
            </span>
          </dt>
          <dd>{imageCollectionEntity.gantry}</dd>
          <dt>
            <span id="creationDate">
              <Translate contentKey="rfidMongodbApp.imageCollection.creationDate">Creation Date</Translate>
            </span>
          </dt>
          <dd>
            {imageCollectionEntity.creationDate ? (
              <TextFormat value={imageCollectionEntity.creationDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/image-collection" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/image-collection/${imageCollectionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ImageCollectionDetail;
