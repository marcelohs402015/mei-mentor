import { AlertCircle, Target, DollarSign } from 'lucide-react';

interface OpportunityCardProps {
  type: 'loss' | 'limit' | 'score';
  title: string;
  value: number | string;
  subtitle: string;
  icon?: React.ReactNode;
}

const OpportunityCard: React.FC<OpportunityCardProps> = ({
  type,
  title,
  value,
  subtitle,
  icon,
}) => {
  const getCardStyles = () => {
    switch (type) {
      case 'loss':
        return {
          bg: 'bg-gradient-to-br from-alert-red-50 to-alert-red-100',
          border: 'border-alert-red-200',
          text: 'text-alert-red-800',
          iconBg: 'bg-alert-red-500',
          icon: icon || <AlertCircle className="w-6 h-6" />,
        };
      case 'limit':
        return {
          bg: 'bg-gradient-to-br from-success-green-50 to-success-green-100',
          border: 'border-success-green-200',
          text: 'text-success-green-800',
          iconBg: 'bg-success-green-500',
          icon: icon || <DollarSign className="w-6 h-6" />,
        };
      case 'score':
        return {
          bg: 'bg-gradient-to-br from-bank-blue-50 to-bank-blue-100',
          border: 'border-bank-blue-200',
          text: 'text-bank-blue-800',
          iconBg: 'bg-bank-blue-500',
          icon: icon || <Target className="w-6 h-6" />,
        };
    }
  };

  const styles = getCardStyles();

  const formatValue = (val: number | string): string => {
    if (typeof val === 'string') return val;
    if (type === 'score') return `${val}%`;
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(val);
  };

  return (
    <div
      className={`${styles.bg} ${styles.border} border-2 rounded-2xl p-6 shadow-card hover:shadow-lg transition-all duration-300 transform hover:-translate-y-1`}
    >
      <div className="flex items-start justify-between mb-4">
        <div className={`${styles.iconBg} p-3 rounded-xl text-white`}>
          {styles.icon}
        </div>
      </div>
      
      <h3 className={`${styles.text} text-sm font-semibold mb-2 uppercase tracking-wide`}>
        {title}
      </h3>
      
      <p className={`${styles.text} text-3xl md:text-4xl font-bold mb-2`}>
        {formatValue(value)}
      </p>
      
      <p className="text-gray-600 text-sm font-medium">{subtitle}</p>
    </div>
  );
};

export default OpportunityCard;

