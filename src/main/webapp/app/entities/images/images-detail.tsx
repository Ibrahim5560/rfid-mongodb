import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './images.reducer';

export const ImagesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const imagesEntity = useAppSelector(state => state.images.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="imagesDetailsHeading">
          <Translate contentKey="rfidMongodbApp.images.detail.title">Images</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{imagesEntity.id}</dd>
          <dt>
            <span id="guid">
              <Translate contentKey="rfidMongodbApp.images.guid">Guid</Translate>
            </span>
          </dt>
          <dd>{imagesEntity.guid}</dd>
          <dt>
            <span id="cmd">
              <Translate contentKey="rfidMongodbApp.images.cmd">Cmd</Translate>
            </span>
          </dt>
          <dd>{imagesEntity.cmd}</dd>
          <dt>
            <span id="gantry">
              <Translate contentKey="rfidMongodbApp.images.gantry">Gantry</Translate>
            </span>
          </dt>
          <dd>{imagesEntity.gantry}</dd>
          <dt>
            <span id="base64">
              <Translate contentKey="rfidMongodbApp.images.base64">Base 64</Translate>
            </span>
          </dt>
          <dd>
            {imagesEntity.base64 ? (
              <div>
                {imagesEntity.base64ContentType ? (
                  <a onClick={openFile(imagesEntity.base64ContentType, imagesEntity.base64)}>
                    <img src={`data:${imagesEntity.base64ContentType};base64,${imagesEntity.base64}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {imagesEntity.base64ContentType}, {byteSize(imagesEntity.base64)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="creationDate">
              <Translate contentKey="rfidMongodbApp.images.creationDate">Creation Date</Translate>
            </span>
          </dt>
          <dd>{imagesEntity.creationDate}</dd>
        </dl>
        <Button tag={Link} to="/images" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/images/${imagesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ImagesDetail;
