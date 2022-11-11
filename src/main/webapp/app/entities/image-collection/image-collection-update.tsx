import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IImageCollection } from 'app/shared/model/image-collection.model';
import { getEntity, updateEntity, createEntity, reset } from './image-collection.reducer';

export const ImageCollectionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const imageCollectionEntity = useAppSelector(state => state.imageCollection.entity);
  const loading = useAppSelector(state => state.imageCollection.loading);
  const updating = useAppSelector(state => state.imageCollection.updating);
  const updateSuccess = useAppSelector(state => state.imageCollection.updateSuccess);

  const handleClose = () => {
    navigate('/image-collection' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.creationDate = convertDateTimeToServer(values.creationDate);

    const entity = {
      ...imageCollectionEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          creationDate: displayDefaultDateTime(),
        }
      : {
          ...imageCollectionEntity,
          creationDate: convertDateTimeFromServer(imageCollectionEntity.creationDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rfidMongodbApp.imageCollection.home.createOrEditLabel" data-cy="ImageCollectionCreateUpdateHeading">
            <Translate contentKey="rfidMongodbApp.imageCollection.home.createOrEditLabel">Create or edit a ImageCollection</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="image-collection-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rfidMongodbApp.imageCollection.guid')}
                id="image-collection-guid"
                name="guid"
                data-cy="guid"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rfidMongodbApp.imageCollection.cmd')}
                id="image-collection-cmd"
                name="cmd"
                data-cy="cmd"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedBlobField
                label={translate('rfidMongodbApp.imageCollection.base64')}
                id="image-collection-base64"
                name="base64"
                data-cy="base64"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('rfidMongodbApp.imageCollection.gantry')}
                id="image-collection-gantry"
                name="gantry"
                data-cy="gantry"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rfidMongodbApp.imageCollection.creationDate')}
                id="image-collection-creationDate"
                name="creationDate"
                data-cy="creationDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/image-collection" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ImageCollectionUpdate;
