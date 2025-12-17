import { api } from '@/lib/axios';
import { OpportunityAnalysisResponse } from '@/types';

export const opportunityService = {
  /**
   * Analyzes opportunity for a customer by CPF.
   * 
   * @param cpf - Customer CPF (11 digits)
   * @returns Opportunity analysis response
   */
  analyzeByCpf: async (cpf: string): Promise<OpportunityAnalysisResponse> => {
    const cleanCpf = cpf.replace(/\D/g, '');
    
    if (cleanCpf.length !== 11) {
      throw new Error('CPF deve conter 11 dígitos');
    }
    
    const response = await api.get<OpportunityAnalysisResponse>(
      `/api/opportunity/${cleanCpf}`
    );
    
    return response.data;
  },
};

