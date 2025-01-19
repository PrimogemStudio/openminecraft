import type {ReactNode} from 'react';
import clsx from 'clsx';
import Heading from '@theme/Heading';
import styles from './styles.module.css';

import Translate, {translate} from '@docusaurus/Translate';

type FeatureItem = {
  title: string;
  Svg: React.ComponentType<React.ComponentProps<'svg'>>;
  textline: string;
  description: ReactNode;
};

const FeatureList: FeatureItem[] = [
  {
    title: translate({id: "ui.main.feature.kt.title"}),
    Svg: require('@site/static/img/kt.svg').default,
    textline: translate({id: "ui.main.feature.kt.subtitle"}),
    description: <Translate id="ui.main.feature.kt"></Translate>
  },
  {
    title: translate({id: "ui.main.feature.api.title"}),
    Svg: require('@site/static/img/undraw_docusaurus_tree.svg').default,
    textline: translate({id: "ui.main.feature.api.subtitle"}),
    description: <Translate id="ui.main.feature.api"></Translate>,
  },
  {
    title: 'Powered by React',
    Svg: require('@site/static/img/undraw_docusaurus_react.svg').default,
    textline: "test",
    description: (
      <>
        Extend or customize your website layout by reusing React. Docusaurus can
        be extended while reusing the same header and footer.
      </>
    ),
  },
];

function Feature({title, Svg, textline, description}: FeatureItem) {
  return (
    <div className={clsx('col col--4')}>
      <div className="text--center">
        <Svg className={styles.featureSvg} role="img" />
      </div>
      <div className="text--center padding-horiz--md">
        <Heading as="h4" style={{color: 'grey', fontStyle: 'italic'}}>{textline}</Heading>
        <Heading as="h3">{title}</Heading>
        <p>{description}</p>
      </div>
    </div>
  );
}

export default function HomepageFeatures(): ReactNode {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
          {FeatureList.map((props, idx) => (
            <Feature key={idx} {...props} />
          ))}
        </div>
      </div>
    </section>
  );
}
