import React from 'react';
import { render } from 'react-dom';
import Menu from './components/Menu';
import data from '../data/recipes';

render(<Menu recipes={data} />, document.getElementById('react-container'));