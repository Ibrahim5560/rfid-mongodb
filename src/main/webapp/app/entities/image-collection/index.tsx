import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ImageCollection from './image-collection';
import ImageCollectionDetail from './image-collection-detail';
import ImageCollectionUpdate from './image-collection-update';
import ImageCollectionDeleteDialog from './image-collection-delete-dialog';

const ImageCollectionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ImageCollection />} />
    <Route path="new" element={<ImageCollectionUpdate />} />
    <Route path=":id">
      <Route index element={<ImageCollectionDetail />} />
      <Route path="edit" element={<ImageCollectionUpdate />} />
      <Route path="delete" element={<ImageCollectionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ImageCollectionRoutes;
